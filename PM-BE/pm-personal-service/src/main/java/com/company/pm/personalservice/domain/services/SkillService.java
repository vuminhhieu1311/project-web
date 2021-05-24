package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.repositories.SkillRepository;
import com.company.pm.personalservice.domain.services.dto.SkillDTO;
import com.company.pm.personalservice.domain.services.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SkillService {
    
    private static final String ENTITY_NAME = "skill";
    
    private final SkillMapper mapper;
    
    private final SkillRepository skillRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<Skill> getSkillsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMapMany(profile -> skillRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Skill> getSkillByProfile(String userId, Long skillId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> skillRepository.findByIdAndPersonalProfile(skillId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            );
    }
    
    @Transactional
    public Mono<Skill> createSkillByProfile(String userId, SkillDTO skillDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> skillRepository.findByPersonalProfile(profile.getId())
                .collectList()
                .flatMap(skills -> {
                    if (skills.size() >= 50) {
                        return Mono.error(new BadRequestAlertException("Exceeded 50 entities", ENTITY_NAME, "limitexceeded"));
                    } else {
                        Skill skill = mapper.skillDTOToSkill(skillDTO);
                        skill.setPersonalProfile(profile);
                        skill.setPersonalProfileId(profile.getId());

                        return skillRepository.save(skill);
                    }
                })
            );
    }
    
    @Transactional
    public Mono<Skill> updateSkillByProfile(String userId, Long skillId, SkillDTO skillDTO) {
        return getSkillByProfile(userId, skillId)
            .flatMap(skill -> {
                Skill update = mapper.skillDTOToSkill(skillDTO);
                
                if (update.getCategory() != null) {
                    skill.setCategory(update.getCategory());
                }
                
                return skillRepository.save(skill);
            });
    }
    
    @Transactional
    public Mono<Void> deleteSkillByProfile(String userId, Long skillId) {
        return getSkillByProfile(userId, skillId)
            .flatMap(skillRepository::delete);
    }
}
