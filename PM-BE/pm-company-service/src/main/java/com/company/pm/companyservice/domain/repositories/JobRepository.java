package com.company.pm.companyservice.domain.repositories;

import com.company.pm.domain.companyservice.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Job entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends R2dbcRepository<Job, Long>, JobRepositoryInternal {
    @Query("SELECT * FROM jobs entity WHERE entity.company_id = :id")
    Flux<Job> findByCompany(Long id);

    @Query("SELECT * FROM jobs entity WHERE entity.company_id IS NULL")
    Flux<Job> findAllWhereCompanyIsNull();
    
    @Query("SELECT * FROM jobs entity WHERE entity.poster_id = :id")
    Flux<Job> findByPoster(String id);
    
    @Query("SELECT * FROM jobs entity WHERE entity.poster_id IS NULL")
    Flux<Job> findAllWherePosterIsNull();
    
    @Query("SELECT * FROM jobs entity WHERE entity.poster_id = :posterId AND entity.id = :jobId")
    Mono<Job> findByPosterAndId(String posterId, Long jobId);

    // just to avoid having unambigous methods
    @Override
    Flux<Job> findAll();

    @Override
    Mono<Job> findById(Long id);

    @Override
    <S extends Job> Mono<S> save(S entity);
}

interface JobRepositoryInternal {
    <S extends Job> Mono<S> insert(S entity);
    <S extends Job> Mono<S> save(S entity);
    Mono<Integer> update(Job entity);

    Flux<Job> findAll();
    Mono<Job> findById(Long id);
    Flux<Job> findAllBy(Pageable pageable);
    Flux<Job> findAllBy(Pageable pageable, Criteria criteria);
    Mono<Job> findOneBy(Criteria criteria);
}
