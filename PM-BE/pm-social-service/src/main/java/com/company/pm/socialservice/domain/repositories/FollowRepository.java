package com.company.pm.socialservice.domain.repositories;

import com.company.pm.domain.socialservice.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Follow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowRepository extends R2dbcRepository<Follow, Long>, FollowRepositoryInternal {
    @Query("SELECT * FROM follow entity WHERE entity.followed_id = :id")
    Flux<Follow> findAllByFollowedId(String id);
    
    @Query("SELECT COUNT(*) FROM follow entity WHERE entity.followed_id = :id")
    Mono<Long> countByFollowedId(String id);
    
    @Query("SELECT * FROM follow entity WHERE entity.follower_id = :id")
    Flux<Follow> findAllByFollowerId(String id);
    
    @Query("SELECT COUNT(*) FROM follow entity WHERE entity.follower_id = :id")
    Mono<Long> countByFollowerId(String id);
    
    @Query("SELECT * FROM follow entity WHERE entity.follower_id = :followerId AND entity.followed_id = :followedId")
    Mono<Follow> findByFollowerIdAndFollowedId(String followerId, String followedId);
    
    @Query("SELECT * FROM follow entity WHERE (entity.follower_id = :id1 AND entity.followed_id = :id2) " +
        "OR (entity.followed_id = :id1 AND entity.follower_id = :id2)")
    Flux<Follow> findAllByFollowerIdAndFollowedId(String id1, String id2);
    
    // just to avoid having unambigous methods
    @Override
    Flux<Follow> findAll();

    @Override
    Mono<Follow> findById(Long id);

    @Override
    <S extends Follow> Mono<S> save(S entity);
}

interface FollowRepositoryInternal {
    <S extends Follow> Mono<S> insert(S entity);
    <S extends Follow> Mono<S> save(S entity);
    Mono<Integer> update(Follow entity);

    Flux<Follow> findAll();
    Mono<Follow> findById(Long id);
    Flux<Follow> findAllBy(Pageable pageable);
    Flux<Follow> findAllBy(Pageable pageable, Criteria criteria);
}
