package com.company.pm.socialservice.domain.repositories;

import com.company.pm.domain.socialservice.Relationship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Relationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipRepository extends R2dbcRepository<Relationship, Long>, RelationshipRepositoryInternal {
    @Query("SELECT * FROM relationship entity WHERE (entity.requester_id = :requesterId AND entity.addressee_id = :addresseeId)" +
        "OR (entity.requester_id = :addresseeId AND entity.addressee_id = :requesterId)")
    Mono<Relationship> findByRequesterIdAndAddresseeId(String requesterId, String addresseeId);
    
    @Query("SELECT * FROM relationship entity WHERE (entity.requester_id = :userId OR entity.addressee_id = :userId) " +
        "AND entity.status = 'ACCEPTED'")
    Flux<Relationship> findFriendsByUser(String userId);
    
    @Query("SELECT * FROM relationship entity WHERE (entity.requester_id = :userId OR entity.addressee_id = :userId) " +
        "AND entity.status = 'PENDING' AND entity.performer_id != :userId")
    Flux<Relationship> findPendingRequestByUser(String userId);
    
    // just to avoid having unambigous methods
    @Override
    Flux<Relationship> findAll();

    @Override
    Mono<Relationship> findById(Long id);

    @Override
    <S extends Relationship> Mono<S> save(S entity);
}

interface RelationshipRepositoryInternal {
    <S extends Relationship> Mono<S> insert(S entity);
    <S extends Relationship> Mono<S> save(S entity);
    Mono<Integer> update(Relationship entity);

    Flux<Relationship> findAll();
    Mono<Relationship> findById(Long id);
    Flux<Relationship> findAllBy(Pageable pageable);
    Flux<Relationship> findAllBy(Pageable pageable, Criteria criteria);
}
