package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Education;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Education entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationRepository extends R2dbcRepository<Education, Long>, EducationRepositoryInternal {
    @Query("SELECT * FROM educations entity WHERE entity.personal_profile_id = :id")
    Flux<Education> findByPersonalProfile(Long id);

    @Query("SELECT * FROM educations entity WHERE entity.personal_profile_id IS NULL")
    Flux<Education> findAllWherePersonalProfileIsNull();
    
    @Query("SELECT * FROM educations entity WHERE entity.id = :educationId AND entity.personal_profile_id = :profileId")
    Mono<Education> findByIdAndPersonalProfile(Long educationId, Long profileId);

    // just to avoid having unambigous methods
    @Override
    Flux<Education> findAll();

    @Override
    Mono<Education> findById(Long id);

    @Override
    <S extends Education> Mono<S> save(S entity);
}

interface EducationRepositoryInternal {
    <S extends Education> Mono<S> insert(S entity);
    <S extends Education> Mono<S> save(S entity);
    Mono<Integer> update(Education entity);

    Flux<Education> findAll();
    Mono<Education> findById(Long id);
    Flux<Education> findAllBy(Pageable pageable);
    Flux<Education> findAllBy(Pageable pageable, Criteria criteria);
}
