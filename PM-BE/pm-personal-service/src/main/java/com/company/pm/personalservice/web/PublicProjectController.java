package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.domain.assembler.PublicProjectRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/users/{id}/projects",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicProjectController {
    
    private final PublicProjectRepresentationModelAssembler assembler;
    
    private final ProjectService projectService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Project>>> getPublicProjects(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Project> projectFlux = projectService.getProjectsByProfile(userId);
        
        return assembler.toCollectionModel(projectFlux, exchange);
    }
}
