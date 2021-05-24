package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.domain.assembler.PublicWorkExperienceRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.WorkExperienceService;
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
    path = "/api/v1/users/{id}/experiences",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicWorkExperienceController {
    
    private final PublicWorkExperienceRepresentationModelAssembler assembler;
    
    private final WorkExperienceService experienceService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<WorkExperience>>> getPublicExps(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<WorkExperience> experienceFlux = experienceService.getExpsByProfile(userId);
        
        return assembler.toCollectionModel(experienceFlux, exchange);
    }
}
