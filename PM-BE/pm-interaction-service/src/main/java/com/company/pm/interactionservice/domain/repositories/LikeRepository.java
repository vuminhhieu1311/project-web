package com.company.pm.interactionservice.domain.repositories;

import com.company.pm.domain.interactionservice.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends R2dbcRepository<Like, Long>, LikeRepositoryInternal {
    
    default Flux<Like> findByPost(Long id) {
        return findAllBy(null, Criteria.where("post_id").is(id));
    }

    @Query("SELECT * FROM likes entity WHERE entity.post_id IS NULL")
    Flux<Like> findAllWherePostIsNull();

    @Query("SELECT * FROM likes entity WHERE entity.user_id = :id")
    Flux<Like> findByUser(String id);

    @Query("SELECT * FROM likes entity WHERE entity.user_id IS NULL")
    Flux<Like> findAllWhereUserIsNull();
    
    @Query("SELECT COUNT(*) FROM likes entity WHERE entity.post_id = :id")
    Mono<Long> countByPost(Long id);
    
    @Query("SELECT * FROM likes entity WHERE entity.user_id = :userId AND entity.post_id = :postId")
    Mono<Like> findByUserAndPost(String userId, Long postId);

    // just to avoid having unambigous methods
    @Override
    Flux<Like> findAll();

    @Override
    Mono<Like> findById(Long id);

    @Override
    <S extends Like> Mono<S> save(S entity);
}

interface LikeRepositoryInternal {
    <S extends Like> Mono<S> insert(S entity);
    <S extends Like> Mono<S> save(S entity);
    Mono<Integer> update(Like entity);

    Flux<Like> findAll();
    Mono<Like> findById(Long id);
    Flux<Like> findAllBy(Pageable pageable);
    Flux<Like> findAllBy(Pageable pageable, Criteria criteria);
}
