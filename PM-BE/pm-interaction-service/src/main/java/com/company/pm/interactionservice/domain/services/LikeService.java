package com.company.pm.interactionservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.Like;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.repositories.LikeRepository;
import com.company.pm.interactionservice.domain.repositories.PostRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LikeService {

    private static final String ENTITY_NAME = "like";
    
    private final PostRepository postRepository;
    
    private final LikeRepository likeRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<Like> getUsersLikePost(Long postId) {
        return findPost(postId)
            .flatMapMany(post -> likeRepository.findByPost(post.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Long> countUsersLikePost(Long postId) {
        return findPost(postId)
            .flatMap(post -> likeRepository.countByPost(post.getId()));
    }
    
    @Transactional
    public Mono<Like> likePostByUser(String userId, Long postId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> findPost(postId)
                .flatMap(post -> {
                    Like like = Like.builder()
                        .user(user)
                        .userId(user.getId())
                        .post(post)
                        .postId(post.getId())
                        .createdAt(Instant.now())
                        .build();

                    return likeRepository.save(like);
                })
            );
    }
    
    @Transactional
    public Mono<Void> unlikePostByUser(String userId, Long postId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> findPost(postId)
                .flatMap(found -> likeRepository.findByUserAndPost(user.getId(), postId))
                .flatMap(likeRepository::delete)
            );
    }
    
    private Mono<Post> findPost(Long postId) {
        return postRepository.findById(postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "post", "idnotfound")));
    }
}
