package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.repositories.WorkExperienceRepository;
import com.company.pm.personalservice.domain.services.dto.WorkExperienceDTO;
import com.company.pm.personalservice.domain.services.mapper.WorkExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class WorkExperienceService {
    
    private final static String ENTITY_NAME = "workExperience";
    
    private final WorkExperienceMapper mapper;
    
    private final WorkExperienceRepository experienceRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<WorkExperience> getExpsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMapMany(profile -> experienceRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<WorkExperience> getExpByProfile(String userId, Long expId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> experienceRepository.findByIdAndPersonalProfile(expId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            );
    }
    
    @Transactional
    public Mono<WorkExperience> createExpByProfile(String userId, WorkExperienceDTO experienceDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> {
                try {
                    WorkExperience experience = mapper.workExpDTOToWorkExp(experienceDTO);
    
                    if (experienceDTO.getIndustry() != null) {
                        profile.setIndustry(experienceDTO.getIndustry());
                    }
                    if (experienceDTO.getHeadline() != null) {
                        profile.setHeadline(experienceDTO.getHeadline());
                    }
                    
                    return profileRepository.save(profile)
                        .flatMap(savedProfile -> {
                            experience.setPersonalProfile(profile);
                            experience.setPersonalProfileId(profile.getId());
                            
                            return experienceRepository.save(experience);
                        });
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<WorkExperience> updateExpByProfile(String userId, Long expId, WorkExperienceDTO experienceDTO) {
        return getExpByProfile(userId, expId)
            .flatMap(experience -> profileRepository.findByUser(userId)
                .flatMap(profile -> {
                    try {
                        WorkExperience update = mapper.workExpDTOToWorkExp(experienceDTO);
        
                        if (experienceDTO.getIndustry() != null) {
                            profile.setIndustry(experienceDTO.getIndustry());
                        }
                        if (experienceDTO.getHeadline() != null) {
                            profile.setHeadline(experienceDTO.getHeadline());
                        }
        
                        if (update.getTitle() != null) {
                            experience.setTitle(update.getTitle());
                        }
                        if (update.getEmploymentType() != null) {
                            experience.setEmploymentType(update.getEmploymentType());
                        }
                        if (update.getCompany() != null) {
                            experience.setCompany(update.getCompany());
                        }
                        if (update.getLocation() != null) {
                            experience.setLocation(update.getLocation());
                        }
                        if (update.getStartDate() != null) {
                            experience.setStartDate(update.getStartDate());
                        }
                        if (update.getEndDate() != null) {
                            experience.setEndDate(update.getEndDate());
                        }
        
                        return profileRepository.save(profile)
                            .flatMap(savedProfile -> {
                                experience.setPersonalProfile(savedProfile);
                
                                return experienceRepository.save(experience);
                            });
                    } catch (ParseException e) {
                        return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                    }
                }));
    }
    
    @Transactional
    public Mono<Void> deleteExpByProfile(String userId, Long expId) {
        return getExpByProfile(userId, expId)
            .flatMap(experienceRepository::delete);
    }
}
