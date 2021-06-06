package com.company.pm.interactionservice.domain.repositories;

import com.company.pm.domain.Attachment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Attachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRepository extends R2dbcRepository<Attachment, Long>, AttachmentRepositoryInternal {
    @Query("SELECT * FROM attachments entity WHERE entity.post_id = :id")
    Flux<Attachment> findByPost(Long id);

    @Query("SELECT * FROM attachments entity WHERE entity.post_id IS NULL")
    Flux<Attachment> findAllWherePostIsNull();

    @Query("SELECT * FROM attachments entity WHERE entity.comment_id = :id")
    Flux<Attachment> findByComment(Long id);

    @Query("SELECT * FROM attachments entity WHERE entity.comment_id IS NULL")
    Flux<Attachment> findAllWhereCommentIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Attachment> findAll();

    @Override
    Mono<Attachment> findById(Long id);

    @Override
    <S extends Attachment> Mono<S> save(S entity);
}

interface AttachmentRepositoryInternal {
    <S extends Attachment> Mono<S> insert(S entity);
    <S extends Attachment> Mono<S> save(S entity);
    Mono<Integer> update(Attachment entity);

    Flux<Attachment> findAll();
    Mono<Attachment> findById(Long id);
    Flux<Attachment> findAllBy(Pageable pageable);
    Flux<Attachment> findAllBy(Pageable pageable, Criteria criteria);
}
