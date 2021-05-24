package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.WorkExperience;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the WorkExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkExperienceRepository extends R2dbcRepository<WorkExperience, Long>, WorkExperienceRepositoryInternal {
    @Query("SELECT * FROM work_experiences entity WHERE entity.personal_profile_id = :id")
    Flux<WorkExperience> findByPersonalProfile(Long id);

    @Query("SELECT * FROM work_experiences entity WHERE entity.personal_profile_id IS NULL")
    Flux<WorkExperience> findAllWherePersonalProfileIsNull();
    
    @Query("SELECT * FROM work_experiences entity WHERE entity.id = :expId AND entity.personal_profile_id = :profileId")
    Mono<WorkExperience> findByIdAndPersonalProfile(Long expId, Long profileId);

    // just to avoid having unambigous methods
    @Override
    Flux<WorkExperience> findAll();

    @Override
    Mono<WorkExperience> findById(Long id);

    @Override
    <S extends WorkExperience> Mono<S> save(S entity);
}

interface WorkExperienceRepositoryInternal {
    <S extends WorkExperience> Mono<S> insert(S entity);
    <S extends WorkExperience> Mono<S> save(S entity);
    Mono<Integer> update(WorkExperience entity);

    Flux<WorkExperience> findAll();
    Mono<WorkExperience> findById(Long id);
    Flux<WorkExperience> findAllBy(Pageable pageable);
    Flux<WorkExperience> findAllBy(Pageable pageable, Criteria criteria);
}
