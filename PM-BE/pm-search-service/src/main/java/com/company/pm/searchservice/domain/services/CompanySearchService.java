package com.company.pm.searchservice.domain.services;

import com.company.pm.domain.searchservice.CompanySearch;
import com.company.pm.searchservice.domain.repositories.CompanySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CompanySearchService {
    
    private final CompanySearchRepository companySearchRepository;
    
    @Transactional(readOnly = true)
    public Flux<CompanySearch> getCompanySearchResults(String query) {
        return companySearchRepository.searchByName(query);
    }
}
