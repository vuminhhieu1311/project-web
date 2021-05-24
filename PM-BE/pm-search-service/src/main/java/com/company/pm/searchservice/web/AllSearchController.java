package com.company.pm.searchservice.web;

import com.company.pm.searchservice.domain.services.AllSearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(
    path = "/api/v1/_search/all"
)
@RequiredArgsConstructor
public class AllSearchController {
    
    private final AllSearchService searchService;
    
    @GetMapping(path = "/{query}")
    public Mono<List<SearchHit>> getSearchResults(
        @PathVariable("query") String query,
        Pageable pageable
    ) {
        return searchService.getSearchResults(query, pageable)
            .collectList();
    }
}
