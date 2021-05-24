package com.company.pm.searchservice.domain.repositories;

import com.company.pm.domain.searchservice.JobSearch;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.MultiMatchQueryBuilder.*;

/**
 * Spring Data Elasticsearch repository for the {@link JobSearch} entity.
 */
public interface JobSearchRepository extends ReactiveElasticsearchRepository<JobSearch, Long>, JobSearchRepositoryInternal {}

interface JobSearchRepositoryInternal {
    Flux<JobSearch> search(String query);
    
    Flux<JobSearch> searchByFilter(MultiValueMap<String, String> queryParams);
}

class JobSearchRepositoryInternalImpl implements JobSearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    JobSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<JobSearch> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return reactiveElasticsearchTemplate.search(nativeSearchQuery, JobSearch.class).map(SearchHit::getContent);
    }
    
    @Override
    public Flux<JobSearch> searchByFilter(MultiValueMap<String, String> queryParams) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        
        String keyword = queryParams.getFirst("keyword");
        String postedFrom = queryParams.getFirst("posted_from");
        String jobType = queryParams.getFirst("job_type");
    
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        
        if (keyword != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(keyword, "title, title._2gram", "title._3gram",
                    "company", "company._2gram", "company._3gram",
                    "location", "location._2gram", "location._3gram"
                ).type(Type.BOOL_PREFIX)
            );
        }
        if (postedFrom != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                rangeQuery("created_at").gte("now-" + postedFrom + "d/d").lt("now/d")
            );
        }
        if (jobType != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                matchQuery("job_type", jobType.toUpperCase())
            );
        }
        
        return reactiveElasticsearchTemplate.search(queryBuilder.withQuery(boolQueryBuilder).build(), JobSearch.class)
            .map(SearchHit::getContent);
    }
}
