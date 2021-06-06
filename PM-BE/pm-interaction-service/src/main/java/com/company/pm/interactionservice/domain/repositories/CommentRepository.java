package com.company.pm.interactionservice.domain.repositories;

import com.company.pm.domain.interactionservice.Comment;
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
 * Spring Data SQL reactive repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends R2dbcRepository<Comment, Long>, CommentRepositoryInternal {
    @Query("SELECT * FROM comments entity WHERE entity.parent_comment_id = :id")
    Flux<Comment> findByParentComment(Long id);

    @Query("SELECT * FROM comments entity WHERE entity.parent_comment_id IS NULL")
    Flux<Comment> findAllWhereParentCommentIsNull();

    @Query("SELECT * FROM comments entity WHERE entity.post_id = :id")
    Flux<Comment> findByPost(Long id);

    @Query("SELECT * FROM comments entity WHERE entity.post_id IS NULL")
    Flux<Comment> findAllWherePostIsNull();
    
    @Query("SELECT * FROM comments entity WHERE entity.author_id = :id")
    Flux<Comment> findByAuthor(String id);
    
    @Query("SELECT * FROM comments entity WHERE entity.author_id IS NULL")
    Flux<Comment> findAllWhereAuthorIsNull();
    
    @Query("SELECT COUNT(*) FROM comments entity WHERE entity.post_id = :id")
    Mono<Long> countByPost(Long id);
    
    @Query("SELECT COUNT(*) FROM comments entity WHERE entity.parent_comment_id = :commentId")
    Mono<Long> countByParentComment(Long commentId);
    
    @Query("SELECT * FROM comments entity WHERE entity.id = :commentId AND entity.author_id = :authorId")
    Mono<Comment> findByIdAndAuthor(Long commentId, String authorId);
    
    default Mono<Comment> findByIdAndPost(Long commentId, Long postId) {
        return findOneBy(Criteria.where("id").is(commentId)
            .and("post_id").is(postId)
        );
    }

    // just to avoid having unambigous methods
    @Override
    Flux<Comment> findAll();

    @Override
    Mono<Comment> findById(Long id);

    @Override
    <S extends Comment> Mono<S> save(S entity);
}

interface CommentRepositoryInternal {
    <S extends Comment> Mono<S> insert(S entity);
    <S extends Comment> Mono<S> save(S entity);
    Mono<Integer> update(Comment entity);

    Flux<Comment> findAll();
    Mono<Comment> findById(Long id);
    Flux<Comment> findAllBy(Pageable pageable);
    Flux<Comment> findAllBy(Pageable pageable, Criteria criteria);
    Mono<Comment> findOneBy(Criteria criteria);
}
