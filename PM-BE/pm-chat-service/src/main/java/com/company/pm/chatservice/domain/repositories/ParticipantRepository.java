package com.company.pm.chatservice.domain.repositories;

import com.company.pm.domain.chatservice.Participant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends R2dbcRepository<Participant, Long>, ParticipantRepositoryInternal {
    
    default Flux<Participant> findByUser(String id) {
        return findAllBy(null, Criteria.where("user_id").is(id));
    }

    @Query("SELECT * FROM participants entity WHERE entity.user_id IS NULL")
    Flux<Participant> findAllWhereUserIsNull();
    
    default Flux<Participant> findByConversation(Long id) {
        return findAllBy(null, Criteria.where("conversation_id").is(id));
    }

    @Query("SELECT * FROM participants entity WHERE entity.conversation_id IS NULL")
    Flux<Participant> findAllWhereConversationIsNull();
    
    default Mono<Participant> findByUserAndConversation(String userId, Long conversationId) {
        return findAllBy(null, Criteria.where("user_id").is(userId)
            .and("conversation_id").is(conversationId))
            .switchIfEmpty(Mono.error(new RuntimeException("Source had 0 elements")))
            .elementAt(0);
    }
    // just to avoid having unambigous methods
    @Override
    Flux<Participant> findAll();

    @Override
    Mono<Participant> findById(Long id);

    @Override
    <S extends Participant> Mono<S> save(S entity);
}

interface ParticipantRepositoryInternal {
    <S extends Participant> Mono<S> insert(S entity);
    <S extends Participant> Mono<S> save(S entity);
    Mono<Integer> update(Participant entity);

    Flux<Participant> findAll();
    Mono<Participant> findById(Long id);
    Flux<Participant> findAllBy(Pageable pageable);
    Flux<Participant> findAllBy(Pageable pageable, Criteria criteria);
}
