package com.company.pm.searchservice.domain.services;

import com.company.pm.domain.searchservice.JobSearch;
import com.company.pm.searchservice.domain.repositories.JobSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class JobSearchService {
    
    private final JobSearchRepository jobSearchRepository;
    
    @Transactional(readOnly = true)
    public Flux<JobSearch> getJobSearchResults(MultiValueMap<String, String> queries) {
        return jobSearchRepository.searchByFilter(queries);
    }
}
