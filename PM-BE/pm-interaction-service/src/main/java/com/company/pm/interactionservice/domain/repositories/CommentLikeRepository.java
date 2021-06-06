package com.company.pm.interactionservice.domain.repositories;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.CommentLike;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the CommentLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentLikeRepository extends R2dbcRepository<CommentLike, Long>, CommentLikeRepositoryInternal {
    @Query("SELECT * FROM comment_likes entity WHERE entity.user_id = :id")
    Flux<CommentLike> findByUser(String id);

    @Query("SELECT * FROM comment_likes entity WHERE entity.user_id IS NULL")
    Flux<CommentLike> findAllWhereUserIsNull();

    default Flux<CommentLike> findByComment(Long id) {
        return findAllBy(null, Criteria.where("comment_id").is(id));
    }

    @Query("SELECT * FROM comment_likes entity WHERE entity.comment_id IS NULL")
    Flux<CommentLike> findAllWhereCommentIsNull();
    
    @Query("SELECT COUNT(*) FROM comment_likes entity WHERE entity.comment_id = :commentId")
    Mono<Long> countByComment(Long commentId);
    
    default Mono<CommentLike> findByUserAndComment(String userId, Long commentId) {
        return findOneBy(Criteria.where("user_id").is(userId)
            .and("comment_id").is(commentId)
        );
    }

    // just to avoid having unambigous methods
    @Override
    Flux<CommentLike> findAll();

    @Override
    Mono<CommentLike> findById(Long id);

    @Override
    <S extends CommentLike> Mono<S> save(S entity);
}

interface CommentLikeRepositoryInternal {
    <S extends CommentLike> Mono<S> insert(S entity);
    <S extends CommentLike> Mono<S> save(S entity);
    Mono<Integer> update(CommentLike entity);

    Flux<CommentLike> findAll();
    Mono<CommentLike> findById(Long id);
    Flux<CommentLike> findAllBy(Pageable pageable);
    Flux<CommentLike> findAllBy(Pageable pageable, Criteria criteria);
    Mono<CommentLike> findOneBy(Criteria criteria);
}
