package com.company.pm.chatservice.domain.repositories;

import com.company.pm.domain.chatservice.Conversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Conversation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversationRepository extends R2dbcRepository<Conversation, Long>, ConversationRepositoryInternal {
    @Query("SELECT * FROM conversations entity WHERE entity.creator_id = :id")
    Flux<Conversation> findByCreator(String id);

    @Query("SELECT * FROM conversations entity WHERE entity.creator_id IS NULL")
    Flux<Conversation> findAllWhereCreatorIsNull();
    
    @Query("SELECT * FROM conversations entity WHERE entity.id = :conversationId AND entity.creator_id = :creatorId")
    Mono<Conversation> findByIdAndCreator(Long conversationId, String creatorId);

    // just to avoid having unambigous methods
    @Override
    Flux<Conversation> findAll();

    @Override
    Mono<Conversation> findById(Long id);

    @Override
    <S extends Conversation> Mono<S> save(S entity);
}

interface ConversationRepositoryInternal {
    <S extends Conversation> Mono<S> insert(S entity);
    <S extends Conversation> Mono<S> save(S entity);
    Mono<Integer> update(Conversation entity);

    Flux<Conversation> findAll();
    Mono<Conversation> findById(Long id);
    Flux<Conversation> findAllBy(Pageable pageable);
    Flux<Conversation> findAllBy(Pageable pageable, Criteria criteria);
}
