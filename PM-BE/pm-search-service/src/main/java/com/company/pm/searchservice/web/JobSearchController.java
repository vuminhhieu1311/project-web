package com.company.pm.searchservice.web;

import com.company.pm.domain.searchservice.JobSearch;
import com.company.pm.searchservice.domain.assembler.JobSearchRepresentationModelAssembler;
import com.company.pm.searchservice.domain.services.JobSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/_search/jobs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class JobSearchController {
    
    private final JobSearchRepresentationModelAssembler assembler;
    
    private final JobSearchService jobSearchService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<JobSearch>>> getSearchResults(
        @ApiIgnore ServerWebExchange exchange
    ) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        Flux<JobSearch> searchFlux = jobSearchService.getJobSearchResults(queryParams);
        
        return assembler.toCollectionModel(searchFlux, exchange);
    }
}
