package com.company.pm.userservice.domain.services;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.searchservice.UserSearch;
import com.company.pm.domain.userservice.Authority;
import com.company.pm.domain.userservice.User;
import com.company.pm.searchservice.domain.repositories.UserSearchRepository;
import com.company.pm.security.utils.SecurityUtils;
import com.company.pm.userservice.domain.services.dto.AdminUserDTO;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import com.company.pm.userservice.domain.services.mapper.UserMapper;
import com.company.pm.userservice.domain.repositories.AuthorityRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    
    private final UserRepository userRepository;
    
    private final UserSearchRepository userSearchRepository;
    
    private final AuthorityRepository authorityRepository;
    
    private final MediaService mediaService;
    
    @Transactional
    public Mono<Void> updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        return SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .flatMap(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                
                user.setLangKey(langKey);
                if (user.getImageUrl() != null) {
                    user.setImageUrl(imageUrl);
                } else {
                    user.setImageUrl(mediaService.getAvatarImgOfUser(user.getId()));
                }
                
                return saveUser(user);
            })
            .flatMap(this::saveUserSearch)
            .doOnNext(user -> log.debug("Changed information of user: {}", user))
            .then();
    }
    
    @Transactional
    public Mono<User> saveUser(User user) {
        return saveUser(user, false);
    }
    
    @Transactional
    public Mono<User> saveUser(User user, boolean forceCreate) {
        return SecurityUtils
            .getCurrentUserLogin()
            .switchIfEmpty(Mono.just(Constants.SYSTEM))
            .flatMap(
                login -> {
                    if (user.getCreatedBy() == null) {
                        user.setCreatedBy(login);
                    }
                    user.setLastModifiedBy(login);
                    // Saving the relationship can be done in an entity callback
                    // once https://github.com/spring-projects/spring-data-r2dbc/issues/215 is done
                    Mono<User> persistedUser;
                    if (forceCreate) {
                        persistedUser = userRepository.create(user);
                    } else {
                        persistedUser = userRepository.save(user);
                    }
                    return persistedUser.flatMap(
                        savedUser -> Flux.fromIterable(user.getAuthorities())
                            .flatMap(authority -> userRepository
                                .saveUserAuthority(savedUser.getId(), authority.getName())
                            )
                            .then(Mono.just(savedUser))
                    );
                }
            );
    }
    
    @Transactional(readOnly = true)
    public Flux<AdminUserDTO> getAllManagedUsers() {
        return userRepository.findAllWithAuthorities().map(userMapper::userToAdminUserDto);
    }
    
    @Transactional(readOnly = true)
    public Flux<UserDTO> getAllPublicUsers() {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue().map(userMapper::userToUserDto);
    }
    
    @Transactional(readOnly = true)
    public Mono<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }
    
    @Transactional(readOnly = true)
    public Flux<String> getAuthorities() {
        return authorityRepository.findAll().map(Authority::getName);
    }
    
    @Transactional
    public Mono<AdminUserDTO> getUserFromAuthentication(AbstractAuthenticationToken authToken) {
        Map<String, Object> attributes;
        
        if (authToken instanceof OAuth2AuthenticationToken) {
            attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
        } else if (authToken instanceof JwtAuthenticationToken) {
            attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
        } else {
            throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
        }
        
        User user = getUser(attributes);
        if (user.getImageUrl() == null) {
            user.setImageUrl(mediaService.getAvatarImgOfUser(user.getId()));
        }
        user.setAuthorities(
            authToken
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(
                    authority -> {
                        Authority auth = Authority.builder().build();
                        auth.setName(authority);
                        
                        return auth;
                    }
                )
                .collect(Collectors.toSet())
        );
        
        return syncUserWithIdP(attributes, user).map(userMapper::userToAdminUserDto);
    }
    
    private static User getUser(Map<String, Object> details) {
        User user = User.builder().build();
        Boolean activated = Boolean.TRUE;
        // handle resource server JWT, where sub claim is email and uid is ID
        if (details.get("uid") != null) {
            user.setId((String) details.get("uid"));
            user.setLogin((String) details.get("sub"));
        } else {
            user.setId((String) details.get("sub"));
        }
        if (details.get("preferred_username") != null) {
            user.setLogin(((String) details.get("preferred_username")).toLowerCase());
        } else if (user.getLogin() == null) {
            user.setLogin(user.getId());
        }
        if (details.get("given_name") != null) {
            user.setFirstName((String) details.get("given_name"));
        }
        if (details.get("family_name") != null) {
            user.setLastName((String) details.get("family_name"));
        }
        if (details.get("email_verified") != null) {
            activated = (Boolean) details.get("email_verified");
        }
        if (details.get("email") != null) {
            user.setEmail(((String) details.get("email")).toLowerCase());
        } else {
            user.setEmail((String) details.get("sub"));
        }
        if (details.get("langKey") != null) {
            user.setLangKey((String) details.get("langKey"));
        } else if (details.get("locale") != null) {
            // trim off country code if it exists
            String locale = (String) details.get("locale");
            if (locale.contains("_")) {
                locale = locale.substring(0, locale.indexOf('_'));
            } else if (locale.contains("-")) {
                locale = locale.substring(0, locale.indexOf('-'));
            }
            user.setLangKey(locale.toLowerCase());
        } else {
            // set langKey to default if not specified by IdP
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        }
        if (details.get("picture") != null) {
            user.setImageUrl((String) details.get("picture"));
        }
        user.setActivated(activated);
        
        return user;
    }
    
    private Mono<User> syncUserWithIdP(Map<String, Object> details, User user) {
        // save authorities in to sync user roles/groups between IdP and JHipster's local database
        Collection<String> userAuthorities = user.getAuthorities().stream().map(Authority::getName)
            .collect(Collectors.toList());
        
        return getAuthorities()
            .collectList()
            .flatMapMany(
                dbAuthorities -> {
                    List<Authority> authoritiesToSave = userAuthorities
                        .stream()
                        .filter(authority -> !dbAuthorities.contains(authority))
                        .map(
                            authority -> {
                                Authority authorityToSave = Authority.builder().build();
                                authorityToSave.setName(authority);
                                
                                return authorityToSave;
                            }
                        )
                        .collect(Collectors.toList());
                    
                    return Flux.fromIterable(authoritiesToSave);
                }
            )
            .doOnNext(authority -> log.debug("Saving authority '{}' in local database", authority))
            .flatMap(authorityRepository::save)
            .then(userRepository.findOneByLogin(user.getLogin()))
            .switchIfEmpty(saveUser(user, true))
            .flatMap(
                existingUser -> {
                    user.setCreatedBy(existingUser.getCreatedBy());
                    user.setCreatedDate(existingUser.getCreatedDate());
                    user.setLastModifiedBy(user.getLogin());
                    // if IdP sends last updated information, use it to determine if an update should happen
                    if (details.get("updated_at") != null) {
                        Instant dbModifiedDate = existingUser.getLastModifiedDate();
                        Instant idpModifiedDate = (Instant) details.get("updated_at");
                        if (idpModifiedDate.isAfter(dbModifiedDate)) {
                            log.debug("Updating user '{}' in local database", user.getLogin());
                            
                            return updateUser(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getLangKey(),
                                user.getImageUrl()
                            );
                        }
                        // no last updated info, blindly update
                    } else {
                        log.debug("Updating user '{}' in local database", user.getLogin());
                        
                        return updateUser(
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getLangKey(),
                            user.getImageUrl()
                        );
                    }
                    
                    return Mono.empty();
                }
            )
            .thenReturn(user);
    }
    
    private Mono<User> saveUserSearch(User user) {
        return userSearchRepository.save(
            new UserSearch(user.getId(), user.getFirstName(), user.getLastName(), user.getImageUrl())
        ).thenReturn(user);
    }
    
    /**
     * Sync with Elasticsearch
     * <p>
     * This is scheduled to get fired everyday, at 02:00 (am).
     */
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncUserSearch() {
        syncUserSearchReactively().blockLast();
    }
    
    @Transactional
    public Flux<UserSearch> syncUserSearchReactively() {
        return userSearchRepository.deleteAll()
            .thenMany(userRepository.findAll()
                .flatMap(this::updateUserSearch)
            );
    }
    
    private Mono<UserSearch> updateUserSearch(User user) {
        return userSearchRepository.save(
            new UserSearch(user.getId(), user.getFirstName(), user.getLastName(), user.getImageUrl())
        )
            .doOnNext(saved -> log.debug("Saved user to elasticsearch: {}", saved));
    }
}
