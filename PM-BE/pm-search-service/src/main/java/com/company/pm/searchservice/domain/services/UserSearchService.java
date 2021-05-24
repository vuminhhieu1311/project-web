package com.company.pm.searchservice.domain.services;

import com.company.pm.domain.searchservice.UserSearch;
import com.company.pm.searchservice.domain.repositories.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserSearchService {
    
    private final UserSearchRepository userSearchRepository;
    
    @Transactional(readOnly = true)
    public Flux<UserSearch> getUserSearchResults(String query) {
        return userSearchRepository.searchByFirstNameOrLastName(query);
    }
}
