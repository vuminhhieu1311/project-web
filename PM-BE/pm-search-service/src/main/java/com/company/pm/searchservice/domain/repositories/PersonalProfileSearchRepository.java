package com.company.pm.searchservice.domain.repositories;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.company.pm.domain.searchservice.PersonalProfileSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * Spring Data Elasticsearch repository for the {@link PersonalProfileSearch} entity.
 */
public interface PersonalProfileSearchRepository
    extends ReactiveElasticsearchRepository<PersonalProfileSearch, Long>, PersonalProfileSearchRepositoryInternal {
    
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
        "                 \"last_name._3gram\"," +
        "                 \"headline\"," +
        "                 \"headline._2gram\"," +
        "                 \"headline._3gram\"" +
        "             ]" +
        "         }" +
        "     }"
    )
    Flux<PersonalProfileSearch> searchByHeadlineOrLastNameOrFirstName(String query);
}

interface PersonalProfileSearchRepositoryInternal {
    Flux<PersonalProfileSearch> search(String query);
}

class PersonalProfileSearchRepositoryInternalImpl implements PersonalProfileSearchRepositoryInternal {
    
    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;
    
    PersonalProfileSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }
    
    @Override
    public Flux<PersonalProfileSearch> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return reactiveElasticsearchTemplate.search(nativeSearchQuery, PersonalProfileSearch.class)
            .map(SearchHit::getContent);
    }
}
