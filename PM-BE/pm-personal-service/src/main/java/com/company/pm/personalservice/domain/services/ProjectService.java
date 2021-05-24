package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Creator;
import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.domain.repositories.CreatorRepository;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.repositories.ProjectRepository;
import com.company.pm.personalservice.domain.services.dto.ProjectDTO;
import com.company.pm.personalservice.domain.services.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private static final String ENTITY_NAME = "project";
    
    private final ProjectMapper mapper;
    
    private final ProjectRepository projectRepository;
    
    private final CreatorRepository creatorRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<Project> getProjectsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMapMany(profile -> projectRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Project> getProjectByProfile(String userId, Long projectId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> projectRepository.findByIdAndPersonalProfile(projectId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            );
    }
    
    @Transactional
    public Mono<Project> createProjectByProfile(String userId, ProjectDTO projectDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> {
                try {
                    Project project = mapper.projectDTOToProject(projectDTO);
                    project.setPersonalProfile(profile);
                    project.setPersonalProfileId(project.getId());
    
                    return projectRepository.save(project)
                        .flatMap(savedProject -> {
                            Set<Creator> creators = savedProject.getCreators().stream()
                                .map(creator -> {
                                    creator.setProject(savedProject);
                                    creator.setProjectId(savedProject.getId());
                    
                                    return creator;
                                })
                                .collect(Collectors.toSet());
            
                            return creatorRepository.saveAll(creators)
                                .collect(Collectors.toSet())
                                .flatMap(savedCreators -> {
                                    savedProject.setCreators(savedCreators);
                    
                                    return projectRepository.save(savedProject);
                                });
                        });
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Project> updateProjectByProfile(String userId, Long projectId, ProjectDTO projectDTO) {
        return getProjectByProfile(userId, projectId)
            .flatMap(project -> {
                try {
                    Project update = mapper.projectDTOToProject(projectDTO);
                    
                    if (update.getName() != null) {
                        project.setName(update.getName());
                    }
                    if (update.getStartDate() != null) {
                        project.setStartDate(update.getStartDate());
                    }
                    if (update.getEndDate() != null) {
                        project.setEndDate(update.getEndDate());
                    }
                    if (update.getUrl() != null) {
                        project.setUrl(update.getUrl());
                    }
                    if (update.getDescription() != null) {
                        project.setDescription(update.getDescription());
                    }
                    
                    return projectRepository.save(project)
                        .flatMap(savedProject -> {
                            if (update.getCreators().size() > 0) {
                                Set<Creator> newCreators = update.getCreators().stream()
                                    .map(creator -> {
                                        creator.setProject(savedProject);
                                        creator.setProjectId(savedProject.getId());
                                        
                                        return creator;
                                    })
                                    .collect(Collectors.toSet());
                                
                                return creatorRepository.saveAll(newCreators)
                                    .collect(Collectors.toSet())
                                    .flatMap(createdCreators -> {
                                        savedProject.getCreators().addAll(createdCreators);
                                        
                                        return projectRepository.save(savedProject);
                                    });
                            } else {
                                return Mono.just(savedProject);
                            }
                        });
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deleteProjectByProfile(String userId, Long projectId) {
        return getProjectByProfile(userId, projectId)
            .flatMap(project -> creatorRepository.deleteAll(project.getCreators())
                .then(projectRepository.delete(project))
            );
    }
}
