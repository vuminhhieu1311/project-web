package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends R2dbcRepository<Project, Long>, ProjectRepositoryInternal {
    @Query("SELECT * FROM projects entity WHERE entity.personal_profile_id = :id")
    Flux<Project> findByPersonalProfile(Long id);

    @Query("SELECT * FROM projects entity WHERE entity.personal_profile_id IS NULL")
    Flux<Project> findAllWherePersonalProfileIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Project> findAll();

    @Override
    Mono<Project> findById(Long id);

    @Override
    <S extends Project> Mono<S> save(S entity);
}

interface ProjectRepositoryInternal {
    <S extends Project> Mono<S> insert(S entity);
    <S extends Project> Mono<S> save(S entity);
    Mono<Integer> update(Project entity);

    Flux<Project> findAll();
    Mono<Project> findById(Long id);
    Flux<Project> findAllBy(Pageable pageable);
    Flux<Project> findAllBy(Pageable pageable, Criteria criteria);
}
