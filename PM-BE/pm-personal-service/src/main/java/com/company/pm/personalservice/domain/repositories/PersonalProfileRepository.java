package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.PersonalProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the PersonalProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalProfileRepository extends R2dbcRepository<PersonalProfile, Long>, PersonalProfileRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<PersonalProfile> findAll();

    @Override
    Mono<PersonalProfile> findById(Long id);
    
    default Mono<PersonalProfile> findByUser(String userId) {
        return findOneBy(Criteria.where("user_id").is(userId));
    }
    
    @Override
    <S extends PersonalProfile> Mono<S> save(S entity);
}

interface PersonalProfileRepositoryInternal {
    <S extends PersonalProfile> Mono<S> insert(S entity);
    <S extends PersonalProfile> Mono<S> save(S entity);
    Mono<Integer> update(PersonalProfile entity);

    Flux<PersonalProfile> findAll();
    Mono<PersonalProfile> findById(Long id);
    Flux<PersonalProfile> findAllBy(Pageable pageable);
    Flux<PersonalProfile> findAllBy(Pageable pageable, Criteria criteria);
    Mono<PersonalProfile> findOneBy(Criteria criteria);
}
