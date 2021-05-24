package com.company.pm.searchservice.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.MultiMatchQueryBuilder.*;

public interface AllSearchRepository {
    Flux<SearchHit> searchAllIndices(String query, Pageable pageable);
}

@Repository
@RequiredArgsConstructor
class AllSearchRepositoryImpl implements AllSearchRepository {
    
    private final ReactiveElasticsearchClient elasticsearchClient;
    
    @Override
    public Flux<SearchHit> searchAllIndices(String query, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(
            query, "name", "name._2gram", "name._3gram",
            "title", "title._2gram", "title._3gram",
            "location", "location._2gram", "location._3gram",
            "headline", "headline._2gram", "headline._3gram",
            "first_name", "first_name._2gram", "first_name._3gram",
            "last_name", "last_name._2gram", "last_name._3gram"
        )
            .type(Type.BOOL_PREFIX);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest = searchRequest.indices("companies", "jobs", "personal_profiles")
            .source(SearchSourceBuilder.searchSource().query(queryBuilder)
                .from(pageable.getPageNumber() - 1)
                .size(pageable.getPageSize())
            );
        
        return elasticsearchClient.search(searchRequest);
    }
}
