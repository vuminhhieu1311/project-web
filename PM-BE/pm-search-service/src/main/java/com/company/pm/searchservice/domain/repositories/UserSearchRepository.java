package com.company.pm.searchservice.domain.repositories;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.company.pm.domain.searchservice.UserSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ReactiveElasticsearchRepository<UserSearch, String>, UserSearchRepositoryInternal {
    @Query(
        "     {" +
        "         \"multi_match\": {" +
        "             \"query\": \"?0\"," +
        "             \"type\": \"bool_prefix\"," +
        "             \"fields\": [" +
        "                 \"first_name\"," +
        "                 \"first_name._2gram\"," +
        "                 \"first_name._3gram\"," +
        "                 \"last_name\"," +
        "                 \"last_name._2gram\"," +
        "                 \"last_name._3gram\"" +
        "             ]" +
        "         }" +
        "     }"
    )
    Flux<UserSearch> searchByFirstNameOrLastName(String query);
}

interface UserSearchRepositoryInternal {
    Flux<UserSearch> search(String query);
}

class UserSearchRepositoryInternalImpl implements UserSearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    UserSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<UserSearch> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return reactiveElasticsearchTemplate.search(nativeSearchQuery, UserSearch.class)
            .map(SearchHit::getContent);
    }
}
