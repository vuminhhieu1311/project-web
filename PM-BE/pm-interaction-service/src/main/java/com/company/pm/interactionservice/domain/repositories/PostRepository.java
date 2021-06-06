package com.company.pm.interactionservice.domain.repositories;

import com.company.pm.domain.interactionservice.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends R2dbcRepository<Post, Long>, PostRepositoryInternal {
    @Query("SELECT * FROM posts entity WHERE entity.author_id = :id")
    Flux<Post> findByAuthor(String id);

    @Query("SELECT * FROM posts entity WHERE entity.author_id IS NULL")
    Flux<Post> findAllWhereAuthorIsNull();
    
    @Query("SELECT * FROM posts entity WHERE entity.author_id = :id AND entity.company_id IS NULL")
    Flux<Post> findByAuthorAndCompanyIsNull(String id);
    
    @Query("SELECT * FROM posts entity WHERE entity.company_id = :id")
    Flux<Post> findByCompany(Long id);
    
    @Query("SELECT * FROM posts entity WHERE entity.id = :id AND entity.company_id = :companyId")
    Mono<Post> findByIdAndCompany(Long id, Long companyId);
    
    @Query("SELECT * FROM posts entity WHERE entity.author_id = :authorId AND entity.company_id = :id")
    Flux<Post> findByAuthorAndCompany(String authorId, Long companyId);
    
    @Query("SELECT * FROM posts entity WHERE entity.id = :id " +
        "AND entity.author_id = :authorId AND entity.company_id = :companyId")
    Mono<Post> findByIdAndAuthorAndCompany(Long id, String authorId, Long companyId);
    
    @Query("SELECT * FROM posts entity WHERE entity.id = :id " +
        "AND entity.author_id = :authorId AND entity.company_id IS NULL")
    Mono<Post> findByIdAndAuthorAndCompanyIsNull(Long id, String authorId);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable != 'PRIVATE' " +
        "AND entity.author_id = :userId AND entity.company_id IS NULL")
    Flux<Post> findVisiblePostsOfUser(String userId);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable != 'PRIVATE' AND entity.author_id = :userId " +
        "AND entity.id = :postId AND entity.company_id IS NULL")
    Mono<Post> findVisiblePostByIdOfUser(String userId, Long postId);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable = 'PUBLIC' " +
        "AND entity.author_id = :userId AND entity.company_id IS NULL")
    Flux<Post> findPublicPostsOfUser(String userId);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable = 'PUBLIC' AND entity.author_id = :userId " +
        "AND entity.id = :postId AND entity.company_id IS NULL")
    Mono<Post> findPublicPostByIdOfUser(String userId, Long postId);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable = 'PUBLIC' " +
        "AND entity.company_id = :id")
    Flux<Post> findPublicPostsOfCompany(Long id);
    
    @Query("SELECT * FROM posts entity WHERE entity.visionable = 'PUBLIC' " +
        "AND entity.id = :postId AND entity.company_id = :companyId")
    Mono<Post> findPublicPostByIdOfCompany(Long companyId, Long postId);
    
    default Flux<Post> findPublicPostsOfUserOrCompany(String id) {
        return findAllBy(null, Criteria.where("visionable").is("PUBLIC")
            .and(Criteria.where("author_id").is(id)
                    .or("company_id").is(id)
            )
        );
    }

    // just to avoid having unambigous methods
    @Override
    Flux<Post> findAll();

    @Override
    Mono<Post> findById(Long id);

    @Override
    <S extends Post> Mono<S> save(S entity);
}

interface PostRepositoryInternal {
    <S extends Post> Mono<S> insert(S entity);
    <S extends Post> Mono<S> save(S entity);
    Mono<Integer> update(Post entity);

    Flux<Post> findAll();
    Mono<Post> findById(Long id);
    Flux<Post> findAllBy(Pageable pageable);
    Flux<Post> findAllBy(Pageable pageable, Criteria criteria);
}
