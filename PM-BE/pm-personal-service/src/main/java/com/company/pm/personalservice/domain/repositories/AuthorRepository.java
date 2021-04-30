package com.company.pm.personalservice.domain.repositories;

import com.company.pm.domain.personalservice.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long>, AuthorRepositoryInternal {
    @Query("SELECT * FROM authors entity WHERE entity.publication_id = :id")
    Flux<Author> findByPublication(Long id);

    @Query("SELECT * FROM authors entity WHERE entity.publication_id IS NULL")
    Flux<Author> findAllWherePublicationIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Author> findAll();

    @Override
    Mono<Author> findById(Long id);

    @Override
    <S extends Author> Mono<S> save(S entity);
}

interface AuthorRepositoryInternal {
    <S extends Author> Mono<S> insert(S entity);
    <S extends Author> Mono<S> save(S entity);
    Mono<Integer> update(Author entity);

    Flux<Author> findAll();
    Mono<Author> findById(Long id);
    Flux<Author> findAllBy(Pageable pageable);
    Flux<Author> findAllBy(Pageable pageable, Criteria criteria);
}
