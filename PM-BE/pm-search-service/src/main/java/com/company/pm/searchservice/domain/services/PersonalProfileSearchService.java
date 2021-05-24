package com.company.pm.searchservice.domain.services;

import com.company.pm.domain.searchservice.PersonalProfileSearch;
import com.company.pm.searchservice.domain.repositories.PersonalProfileSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PersonalProfileSearchService {
    
    private final PersonalProfileSearchRepository profileSearchRepository;
    
    @Transactional(readOnly = true)
    public Flux<PersonalProfileSearch> getProfileSearchResults(String query) {
        return profileSearchRepository.searchByHeadlineOrLastNameOrFirstName(query);
    }
}
