package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Certification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Certification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificationRepository extends R2dbcRepository<Certification, Long>, CertificationRepositoryInternal {
    @Query("SELECT * FROM certifications entity WHERE entity.personal_profile_id = :id")
    Flux<Certification> findByPersonalProfile(Long id);

    @Query("SELECT * FROM certifications entity WHERE entity.personal_profile_id IS NULL")
    Flux<Certification> findAllWherePersonalProfileIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Certification> findAll();

    @Override
    Mono<Certification> findById(Long id);

    @Override
    <S extends Certification> Mono<S> save(S entity);
}

interface CertificationRepositoryInternal {
    <S extends Certification> Mono<S> insert(S entity);
    <S extends Certification> Mono<S> save(S entity);
    Mono<Integer> update(Certification entity);

    Flux<Certification> findAll();
    Mono<Certification> findById(Long id);
    Flux<Certification> findAllBy(Pageable pageable);
    Flux<Certification> findAllBy(Pageable pageable, Criteria criteria);
}
