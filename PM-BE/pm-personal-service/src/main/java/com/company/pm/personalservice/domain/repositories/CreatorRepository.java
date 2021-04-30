package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Creator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Creator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreatorRepository extends R2dbcRepository<Creator, Long>, CreatorRepositoryInternal {
    @Query("SELECT * FROM creators entity WHERE entity.project_id = :id")
    Flux<Creator> findByProject(Long id);

    @Query("SELECT * FROM creators entity WHERE entity.project_id IS NULL")
    Flux<Creator> findAllWhereProjectIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Creator> findAll();

    @Override
    Mono<Creator> findById(Long id);

    @Override
    <S extends Creator> Mono<S> save(S entity);
}

interface CreatorRepositoryInternal {
    <S extends Creator> Mono<S> insert(S entity);
    <S extends Creator> Mono<S> save(S entity);
    Mono<Integer> update(Creator entity);

    Flux<Creator> findAll();
    Mono<Creator> findById(Long id);
    Flux<Creator> findAllBy(Pageable pageable);
    Flux<Creator> findAllBy(Pageable pageable, Criteria criteria);
}
