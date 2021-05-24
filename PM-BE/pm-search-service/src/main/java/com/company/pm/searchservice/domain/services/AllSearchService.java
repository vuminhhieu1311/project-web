package com.company.pm.searchservice.domain.services;

import com.company.pm.searchservice.domain.repositories.AllSearchRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AllSearchService {
    
    private final AllSearchRepository searchRepository;
    
    @Transactional(readOnly = true)
    public Flux<SearchHit> getSearchResults(String query, Pageable pageable) {
        return searchRepository.searchAllIndices(query, pageable);
    }
}
