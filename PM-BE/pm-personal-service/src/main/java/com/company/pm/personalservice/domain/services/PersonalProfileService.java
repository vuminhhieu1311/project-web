package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.domain.searchservice.PersonalProfileSearch;
import com.company.pm.domain.searchservice.UserSearch;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import com.company.pm.personalservice.domain.services.mapper.PersonalProfileMapper;
import com.company.pm.searchservice.domain.repositories.PersonalProfileSearchRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalProfileService {
    
    private static final String ENTITY_NAME = "personal_profile";
    
    private final PersonalProfileMapper mapper;
    
    private final PersonalProfileRepository profileRepository;
    
    private final PersonalProfileSearchRepository profileSearchRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Mono<PersonalProfile> getProfileByUser(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional
    public Mono<PersonalProfile> createProfileByUser(String userId, PersonalProfileDTO profileDTO) {
         return userRepository.findById(userId)
             .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
             .flatMap(user -> {
                 try {
                     PersonalProfile profile = mapper.profileDtoToProfile(profileDTO);
                     profile.setUser(user);
                     profile.setUserId(user.getId());
                     log.debug("profile: {}", profile);
    
                     return profileRepository.findByUser(userId)
                         .switchIfEmpty(profileRepository.save(profile))
                         .flatMap(this::saveProfileSearch);
                 } catch (ParseException e) {
                     return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                 }
             });
    }
    
    @Transactional
    public Mono<PersonalProfile> updateProfileByUser(String userId, PersonalProfileDTO profileDTO) {
        return getProfileByUser(userId)
            .flatMap(profile -> {
                try {
                    PersonalProfile update = mapper.profileDtoToProfile(profileDTO);
    
                    if (update.getHeadline() != null) {
                        profile.setHeadline(update.getHeadline());
                    }
                    if (update.getCountry() != null) {
                        profile.setCountry(update.getCountry());
                    }
                    if (update.getLocation() != null) {
                        profile.setLocation(update.getLocation());
                    }
                    if (update.getIndustry() != null) {
                        profile.setIndustry(update.getIndustry());
                    }
                    if (update.getBirthday() != null) {
                        profile.setBirthday(update.getBirthday());
                    }
                    if (update.getPhoneNumber() != null) {
                        profile.setPhoneNumber(update.getPhoneNumber());
                    }
                    if (update.getAddress() != null) {
                        profile.setAddress(update.getAddress());
                    }
                    if (update.getAbout() != null) {
                        profile.setAbout(update.getAbout());
                    }
                    if (update.getBgImageUrl() != null) {
                        profile.setBgImageUrl(update.getBgImageUrl());
                    }
    
                    return profileRepository.save(profile)
                        .flatMap(this::saveProfileSearch);
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    private Mono<PersonalProfile> saveProfileSearch(PersonalProfile p) {
        return profileSearchRepository.save(new PersonalProfileSearch(
            p.getId(), p.getHeadline(), p.getUser().getFirstName(), p.getUser().getLastName()
        )).thenReturn(p);
    }
    
    /**
     * Sync with Elasticsearch
     * <p>
     * This is scheduled to get fired everyday, at 02:00 (am).
     */
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncProfileSearch() {
        syncProfileSearchReactively().blockLast();
    }
    
    @Transactional
    public Flux<PersonalProfileSearch> syncProfileSearchReactively() {
        return profileSearchRepository.deleteAll()
            .thenMany(profileRepository.findAll()
                .flatMap(this::updateProfileSearch)
            );
    }
    
    private Mono<PersonalProfileSearch> updateProfileSearch(PersonalProfile profile) {
        return profileSearchRepository.save(new PersonalProfileSearch(
            profile.getId(),
            profile.getHeadline(),
            profile.getUser().getFirstName(),
            profile.getUser().getLastName()
        ))
            .doOnNext(saved -> log.debug("Saved profile to elasticsearch: {}", saved));
    }
}
