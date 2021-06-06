package com.company.pm.searchservice.web;

import com.company.pm.domain.searchservice.PersonalProfileSearch;
import com.company.pm.searchservice.domain.assembler.ProfileSearchRepresentationModelAssembler;
import com.company.pm.searchservice.domain.services.PersonalProfileSearchService;
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
    path = "/api/v1/_search/profile",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class ProfileSearchController {
    
    private final ProfileSearchRepresentationModelAssembler assembler;
    
    private final PersonalProfileSearchService profileSearchService;
    
    @GetMapping(path = "/{query}")
    public Mono<CollectionModel<EntityModel<PersonalProfileSearch>>> getProfileSearchResults(
        @PathVariable("query") String query,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<PersonalProfileSearch> searchFlux = profileSearchService.getProfileSearchResults(query);
        
        return assembler.toCollectionModel(searchFlux, exchange);
    }
}
