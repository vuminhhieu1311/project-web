package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.domain.repositories.EducationRepository;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.services.dto.EducationDTO;
import com.company.pm.personalservice.domain.services.mapper.EducationMapper;
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
public class EducationService {

    private static final String ENTITY_NAME = "education";
    
    private final EducationMapper mapper;
    
    private final EducationRepository educationRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<Education> getEducationsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMapMany(profile -> educationRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Education> getEducationByProfile(String userId, Long educationId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> educationRepository.findByIdAndPersonalProfile(educationId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            );
    }
    
    @Transactional
    public Mono<Education> createEducationByProfile(String userId, EducationDTO educationDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> {
                try {
                    Education education = mapper.educationDTOToEducation(educationDTO);
                    education.setPersonalProfile(profile);
                    education.setPersonalProfileId(profile.getId());
                    
                    return educationRepository.save(education);
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Education> updateEducationByProfile(String userId, Long educationId, EducationDTO educationDTO) {
        return getEducationByProfile(userId, educationId)
            .flatMap(education -> {
                try {
                    Education update = mapper.educationDTOToEducation(educationDTO);
                    
                    if (update.getSchool() != null) {
                        education.setSchool(update.getSchool());
                    }
                    if (update.getDegree() != null) {
                        education.setDegree(update.getDegree());
                    }
                    if (update.getFieldOfStudy() != null) {
                        education.setFieldOfStudy(update.getFieldOfStudy());
                    }
                    if (update.getStartDate() != null) {
                        education.setStartDate(update.getStartDate());
                    }
                    if (update.getEndDate() != null) {
                        education.setEndDate(update.getEndDate());
                    }
                    if (update.getGrade() != null) {
                        education.setGrade(update.getGrade());
                    }
                    if (update.getActivities() != null) {
                        education.setActivities(update.getActivities());
                    }
                    if (update.getDescription() != null) {
                        education.setDescription(update.getDescription());
                    }
                    
                    return educationRepository.save(education);
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deleteEducationByProfile(String userId, Long educationId) {
        return getEducationByProfile(userId, educationId)
            .flatMap(educationRepository::delete);
    }
}
