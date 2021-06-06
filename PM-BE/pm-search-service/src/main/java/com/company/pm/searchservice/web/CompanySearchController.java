package com.company.pm.searchservice.web;

import com.company.pm.domain.searchservice.CompanySearch;
import com.company.pm.searchservice.domain.assembler.CompanySearchRepresentationModelAssembler;
import com.company.pm.searchservice.domain.services.CompanySearchService;
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
    path = "/api/v1/_search/companies",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CompanySearchController {
    
    private final CompanySearchRepresentationModelAssembler assembler;
    
    private final CompanySearchService companySearchService;
    
    @GetMapping(path = "/{query}")
    public Mono<CollectionModel<EntityModel<CompanySearch>>> getCompanySearchResults(
        @PathVariable("query") String query,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<CompanySearch> searchFlux = companySearchService.getCompanySearchResults(query);
        
        return assembler.toCollectionModel(searchFlux, exchange);
    }
}
