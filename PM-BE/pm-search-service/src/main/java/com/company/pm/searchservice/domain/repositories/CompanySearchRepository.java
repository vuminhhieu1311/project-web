package com.company.pm.searchservice.domain.repositories;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.company.pm.domain.searchservice.CompanySearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * Spring Data Elasticsearch repository for the {@link CompanySearch} entity.
 */
public interface CompanySearchRepository extends ReactiveElasticsearchRepository<CompanySearch, Long>, CompanySearchRepositoryInternal {
    @Query(
            "     {" +
            "         \"multi_match\": {" +
            "             \"query\": \"?0\"," +
            "             \"type\": \"bool_prefix\"," +
            "             \"fields\": [" +
            "                 \"name\"," +
            "                 \"name._2gram\"," +
            "                 \"name._3gram\"" +
            "             ]" +
            "         }" +
            "     }"
    )
    Flux<CompanySearch> searchByName(String query);
}

interface CompanySearchRepositoryInternal {
    Flux<CompanySearch> search(String query);
}

class CompanySearchRepositoryInternalImpl implements CompanySearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    CompanySearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<CompanySearch> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return reactiveElasticsearchTemplate.search(nativeSearchQuery, CompanySearch.class).map(SearchHit::getContent);
    }
}
