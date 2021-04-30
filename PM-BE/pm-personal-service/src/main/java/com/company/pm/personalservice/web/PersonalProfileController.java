package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.services.PersonalProfileService;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/users/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PersonalProfileController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "personalProfile";
    
    private final PersonalProfileService personalProfileService;
    
    @GetMapping(path = "/profile")
    public Mono<ResponseEntity<PersonalProfile>> getPersonalProfile(
        @PathVariable("id") String userId
    ) {
         Mono<PersonalProfile> profileMono = personalProfileService.getProfileByUser(userId);
         
         return ResponseUtil.wrapOrNotFound(profileMono);
    }
    
    @PatchMapping(
        path = "/profile",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<PersonalProfile>> updateIntroProfile(
        @PathVariable("id") String userId,
        @Valid PersonalProfileDTO profileDTO
    ) {
        Mono<PersonalProfile> profileMono = personalProfileService.updateIntroProfile(userId, profileDTO);
        
        return profileMono
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(res ->
                ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId().toString()))
                    .body(res)
            );
    }
}
