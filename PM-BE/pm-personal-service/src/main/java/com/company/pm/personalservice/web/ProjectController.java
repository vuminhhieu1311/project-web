package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.domain.assembler.ProjectRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.ProjectService;
import com.company.pm.personalservice.domain.services.dto.ProjectDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/profile/projects",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class ProjectController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "project";
    
    private final ProjectRepresentationModelAssembler assembler;
    
    private final ProjectService projectService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Project>>> getProjects(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Project> projectFlux = projectService.getProjectsByProfile(user.getId());
                            
                            return assembler.toCollectionModel(projectFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/{projectId}")
    public Mono<EntityModel<Project>> getProject(
        @PathVariable("projectId") Long projectId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> projectService.getProjectByProfile(user.getId(), projectId)
                            .flatMap(project -> assembler.toModel(project, exchange))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Project>>> createProject(
        @Valid ProjectDTO projectDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> projectService.createProjectByProfile(user.getId(), projectDTO)
                            .flatMap(project -> assembler
                                .toModel(project, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, project.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PatchMapping(
        path = "/{projectId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Project>>> updateProject(
        @PathVariable("projectId") Long projectId,
        ProjectDTO projectDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> projectService.updateProjectByProfile(user.getId(), projectId, projectDTO)
                            .flatMap(project -> assembler
                                .toModel(project, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, project.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{projectId}")
    public Mono<ResponseEntity<Void>> deleteProject(
        @PathVariable("projectId") Long projectId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> projectService.deleteProjectByProfile(user.getId(), projectId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, projectId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
