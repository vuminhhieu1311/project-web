package com.company.pm.interactionservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.domain.interactionservice.CommentLike;
import com.company.pm.interactionservice.domain.repositories.CommentLikeRepository;
import com.company.pm.interactionservice.domain.repositories.CommentRepository;
import com.company.pm.interactionservice.domain.repositories.PostRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import com.company.pm.userservice.domain.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    
    private static final String ENTITY_NAME = "comment_like";
    
    private final UserMapper userMapper;
    
    private final PostRepository postRepository;
    
    private final CommentLikeRepository commentLikeRepository;
    
    private final CommentRepository commentRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<CommentLike> getUsersLikeCommentOfPost(Long postId, Long commentId) {
        return findPostAndComment(postId, commentId)
            .flatMapMany(found -> commentLikeRepository.findByComment(commentId));
    }
    
    @Transactional(readOnly = true)
    public Mono<Long> countUsersLikeCommentOfPost(Long postId, Long commentId) {
        return findPostAndComment(postId, commentId)
            .flatMap(found -> commentLikeRepository.countByComment(commentId));
    }
    
    @Transactional
    public Mono<CommentLike> likeCommentByUser(String userId, Long postId, Long commentId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> findPostAndComment(postId, commentId)
                .flatMap(found -> commentRepository.findById(commentId)
                    .flatMap(comment -> {
                        CommentLike commentLike = CommentLike.builder()
                            .comment(comment)
                            .commentId(comment.getId())
                            .user(user)
                            .userId(user.getId())
                            .createdAt(Instant.now())
                            .build();
                        
                        return commentLikeRepository.save(commentLike);
                    })
                )
            );
    }
    
    @Transactional
    public Mono<Void> unlikeCommentByUser(String userId, Long postId, Long commentId) {
        return findPostAndComment(postId, commentId)
            .flatMap(found -> commentLikeRepository.findByUserAndComment(userId, commentId)
                .flatMap(commentLikeRepository::delete)
            );
    }
    
    private Mono<Comment> findPostAndComment(Long postId, Long commentId) {
        return postRepository.findById(postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "post", "idnotfound")))
            .flatMap(foundPost -> commentRepository.findById(commentId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "comment", "idnotfound")))
            );
    }
}
