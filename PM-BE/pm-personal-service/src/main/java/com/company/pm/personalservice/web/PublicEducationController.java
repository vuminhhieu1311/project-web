package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.domain.assembler.PublicEducationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.EducationService;
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
    path = "/api/v1/users/{id}/edus",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicEducationController {
    
    private final PublicEducationRepresentationModelAssembler assembler;
    
    private final EducationService educationService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Education>>> getPublicEdus(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Education> educationFlux = educationService.getEducationsByProfile(userId);
        
        return assembler.toCollectionModel(educationFlux, exchange);
    }
}
