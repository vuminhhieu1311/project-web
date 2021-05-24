package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.services.PersonalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/api/v1/users/{id}/profile",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicProfileController {
    
    private final PersonalProfileService profileService;
    
    @GetMapping
    public Mono<PersonalProfile> getPublicProfile(@PathVariable("id") String userId) {
        return profileService.getProfileByUser(userId);
    }
}
