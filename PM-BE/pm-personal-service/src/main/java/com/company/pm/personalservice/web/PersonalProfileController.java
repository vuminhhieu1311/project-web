package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.services.PersonalProfileService;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/profile",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PersonalProfileController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "personal_profile";
    
    private final PersonalProfileService profileService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<ResponseEntity<PersonalProfile>> getPersonalProfile(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Mono<PersonalProfile> profileMono = profileService.getProfileByUser(user.getId());
    
                            return profileMono.map(ResponseEntity::ok);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<PersonalProfile>> createProfile(
        @Valid PersonalProfileDTO profileDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> profileService.createProfileByUser(user.getId(), profileDTO)
                            .map(profile -> ResponseEntity
                                .created(exchange.getRequest().getURI())
                                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, profile.getId().toString()))
                                .body(profile)
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PatchMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<PersonalProfile>> updateProfile(
        PersonalProfileDTO profileDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Mono<PersonalProfile> profileMono = profileService.updateProfileByUser(user.getId(), profileDTO);
    
                            return profileMono
                                .map(profile -> ResponseEntity
                                    .ok()
                                    .headers(
                                        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profile.getId().toString()))
                                    .body(profile)
                                );
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
