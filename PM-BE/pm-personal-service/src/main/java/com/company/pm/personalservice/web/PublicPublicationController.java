package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.domain.assembler.PublicPublicationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.PublicationService;
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
    path = "/api/v1/users/{id}/pubs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicPublicationController {
    
    private final PublicPublicationRepresentationModelAssembler assembler;
    
    private final PublicationService publicationService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Publication>>> getPublicPubs(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Publication> publicationFlux = publicationService.getPubsByProfile(userId);
        
        return assembler.toCollectionModel(publicationFlux, exchange);
    }
}
