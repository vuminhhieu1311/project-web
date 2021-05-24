package com.company.pm.searchservice.web;

import com.company.pm.domain.searchservice.UserSearch;
import com.company.pm.searchservice.domain.assembler.UserSearchRepresentationModelAssembler;
import com.company.pm.searchservice.domain.services.UserSearchService;
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
    path = "/api/v1/_search/users",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class UserSearchController {
    
    private final UserSearchRepresentationModelAssembler assembler;
    
    private final UserSearchService userSearchService;
    
    @GetMapping(path = "/{query}")
    public Mono<CollectionModel<EntityModel<UserSearch>>> getSearchResult(
        @PathVariable("query") String query,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<UserSearch> searchFlux = userSearchService.getUserSearchResults(query);
        
        return assembler.toCollectionModel(searchFlux, exchange);
    }
}
