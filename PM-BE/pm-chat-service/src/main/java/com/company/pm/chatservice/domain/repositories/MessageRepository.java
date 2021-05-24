package com.company.pm.chatservice.domain.repositories;

import com.company.pm.domain.chatservice.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends R2dbcRepository<Message, Long>, MessageRepositoryInternal {
    @Query("SELECT * FROM messages entity WHERE entity.conversation_id = :id")
    Flux<Message> findByConversation(Long id);

    @Query("SELECT * FROM messages entity WHERE entity.conversation_id IS NULL")
    Flux<Message> findAllWhereConversationIsNull();

    @Query("SELECT * FROM messages entity WHERE entity.sender_id = :id")
    Flux<Message> findBySender(String id);

    @Query("SELECT * FROM messages entity WHERE entity.sender_id IS NULL")
    Flux<Message> findAllWhereSenderIsNull();
    
    @Query("SELECT * FROM messages entity WHERE entity.conversation_id = :id AND entity.deleted_at IS NULL")
    Flux<Message> findAllByConversationAndDeletedAtIsNull(String id);

    // just to avoid having unambigous methods
    @Override
    Flux<Message> findAll();

    @Override
    Mono<Message> findById(Long id);

    @Override
    <S extends Message> Mono<S> save(S entity);
}

interface MessageRepositoryInternal {
    <S extends Message> Mono<S> insert(S entity);
    <S extends Message> Mono<S> save(S entity);
    Mono<Integer> update(Message entity);

    Flux<Message> findAll();
    Mono<Message> findById(Long id);
    Flux<Message> findAllBy(Pageable pageable);
    Flux<Message> findAllBy(Pageable pageable, Criteria criteria);
}
