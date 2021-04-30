package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import com.company.pm.personalservice.domain.services.mapper.PersonalProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonalProfileService {
    
    private static final String ENTITY_NAME = "personalProfile";
    
    private final PersonalProfileMapper profileMapper;
    
    private final PersonalProfileRepository personalProfileRepository;
    
    @Transactional(readOnly = true)
    public Mono<PersonalProfile> getProfileByUser(String userId) {
        return personalProfileRepository.findByUser(userId);
    }
    
    @Transactional
    public Mono<PersonalProfile> updateIntroProfile(String userId, PersonalProfileDTO profileDTO) {
        return getProfileByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idinvalid")))
            .map(profile -> {
                PersonalProfile update = profileMapper.profileDtoToProfile(profileDTO);
    
                if (update.getBirthday() != null) {
                    profile.setBirthday(update.getBirthday());
                }
                if (update.getLocation() != null) {
                    profile.setLocation(update.getLocation());
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
                
                return profile;
            })
            .flatMap(personalProfileRepository::save);
    }
}
