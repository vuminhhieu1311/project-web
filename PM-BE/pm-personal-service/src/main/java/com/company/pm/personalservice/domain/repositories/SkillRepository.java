package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Skill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends R2dbcRepository<Skill, Long>, SkillRepositoryInternal {
    @Query("SELECT * FROM skills entity WHERE entity.personal_profile_id = :id")
    Flux<Skill> findByPersonalProfile(Long id);

    @Query("SELECT * FROM skills entity WHERE entity.personal_profile_id IS NULL")
    Flux<Skill> findAllWherePersonalProfileIsNull();
    
    @Query("SELECT * FROM skills entity WHERE entity.id = :skillId AND entity.personal_profile_id = :profileId")
    Mono<Skill> findByIdAndPersonalProfile(Long skillId, Long profileId);

    // just to avoid having unambigous methods
    @Override
    Flux<Skill> findAll();

    @Override
    Mono<Skill> findById(Long id);

    @Override
    <S extends Skill> Mono<S> save(S entity);
}

interface SkillRepositoryInternal {
    <S extends Skill> Mono<S> insert(S entity);
    <S extends Skill> Mono<S> save(S entity);
    Mono<Integer> update(Skill entity);

    Flux<Skill> findAll();
    Mono<Skill> findById(Long id);
    Flux<Skill> findAllBy(Pageable pageable);
    Flux<Skill> findAllBy(Pageable pageable, Criteria criteria);
}
