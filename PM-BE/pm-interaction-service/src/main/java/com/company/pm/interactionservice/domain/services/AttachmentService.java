package com.company.pm.interactionservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.Attachment;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.repositories.AttachmentRepository;
import com.company.pm.interactionservice.domain.repositories.CommentRepository;
import com.company.pm.interactionservice.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    
    private static final String ENTITY_NAME = "attachment";
    
    private final AttachmentRepository attachmentRepository;
    
    private final PostRepository postRepository;
    
    private final CommentRepository commentRepository;
    
    @Transactional(readOnly = true)
    public Flux<Attachment> getAttachmentsOfPost(Long postId) {
        return findPost(postId)
            .flatMapMany(post -> attachmentRepository.findByPost(postId));
    }
    
    @Transactional(readOnly = true)
    public Mono<Attachment> getAttachmentOfPost(Long postId, Long attachmentId) {
        return findPost(postId)
            .flatMap(found -> attachmentRepository.findById(attachmentId));
    }
    
    private Mono<Post> findPost(Long postId) {
        return postRepository.findById(postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not fouund", "post", "idnotfound")));
    }
}
