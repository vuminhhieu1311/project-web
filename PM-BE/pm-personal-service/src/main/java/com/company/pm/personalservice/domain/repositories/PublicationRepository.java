package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Publication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Publication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationRepository extends R2dbcRepository<Publication, Long>, PublicationRepositoryInternal {
    @Query("SELECT * FROM publications entity WHERE entity.personal_profile_id = :id")
    Flux<Publication> findByPersonalProfile(Long id);

    @Query("SELECT * FROM publications entity WHERE entity.personal_profile_id IS NULL")
    Flux<Publication> findAllWherePersonalProfileIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Publication> findAll();

    @Override
    Mono<Publication> findById(Long id);

    @Override
    <S extends Publication> Mono<S> save(S entity);
}

interface PublicationRepositoryInternal {
    <S extends Publication> Mono<S> insert(S entity);
    <S extends Publication> Mono<S> save(S entity);
    Mono<Integer> update(Publication entity);

    Flux<Publication> findAll();
    Mono<Publication> findById(Long id);
    Flux<Publication> findAllBy(Pageable pageable);
    Flux<Publication> findAllBy(Pageable pageable, Criteria criteria);
}
