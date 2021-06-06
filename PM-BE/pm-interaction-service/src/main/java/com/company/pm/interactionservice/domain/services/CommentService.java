package com.company.pm.interactionservice.domain.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.repositories.CompanyRepository;
import com.company.pm.domain.Attachment;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.domain.userservice.User;
import com.company.pm.interactionservice.domain.repositories.AttachmentRepository;
import com.company.pm.interactionservice.domain.repositories.CommentRepository;
import com.company.pm.interactionservice.domain.repositories.PostRepository;
import com.company.pm.interactionservice.domain.services.dto.CommentDTO;
import com.company.pm.interactionservice.domain.services.mapper.CommentMapper;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private static final String ENTITY_NAME = "comment";
    
    private static final String UPLOAD_FOLDER = "personal-management-collection";
    
    private final CommentMapper mapper;
    
    private final Cloudinary cloudinary;
    
    private final CommentRepository commentRepository;
    
    private final PostRepository postRepository;
    
    private final UserRepository userRepository;
    
    private final CompanyRepository companyRepository;
    
    private final AttachmentRepository attachmentRepository;
    
    @Transactional(readOnly = true)
    public Flux<Comment> getCommentsOfPost(Long postId) {
        return findPost(postId)
            .flatMapMany(post -> commentRepository.findByPost(post.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Long> countCommentsOfPost(Long postId) {
        return findPost(postId)
            .flatMap(post -> commentRepository.countByPost(post.getId()));
    }
    
    @Transactional(readOnly = true)
    public Flux<Comment> getRepliesOfComment(Long postId, Long cmtId) {
        return findPost(postId)
            .flatMapMany(found -> commentRepository.findByIdAndPost(cmtId, postId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "comment", "idnotfound")))
                .flatMapMany(comment -> commentRepository.findByParentComment(comment.getId()))
            );
    }
    
    @Transactional(readOnly = true)
    public Mono<Long> countRepliesOfComment(Long postId, Long cmtId) {
        return findPost(postId)
            .flatMap(found -> commentRepository.findByIdAndPost(cmtId, postId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "comment", "idnotfound")))
                .flatMap(comment -> commentRepository.countByParentComment(comment.getId()))
            );
    }
    
    @Transactional
    public Mono<Comment> createCommentToPost(String userId, Long postId, CommentDTO commentDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> findPost(postId)
                 .flatMap(post -> {
                     Comment comment = mapper.commentDTOToComment(commentDTO);
                     comment.setAuthor(user);
                     comment.setAuthorId(user.getId());
                     comment.setPost(post);
                     comment.setPostId(post.getId());
                     comment.setCreatedAt(Instant.now());
                     comment.setUpdatedAt(Instant.now());
    
                     return saveComment(user, comment);
                 })
            );
    }
    
    @Transactional
    public Mono<Comment> createReplyToComment(String userId, Long postId, Long commentId, CommentDTO commentDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> findPost(postId)
                .flatMap(found -> commentRepository.findById(commentId)
                    .flatMap(parentComment -> {
                        Comment comment = mapper.commentDTOToComment(commentDTO);
                        comment.setParentComment(parentComment);
                        comment.setParentCommentId(parentComment.getId());
                        comment.setAuthor(user);
                        comment.setAuthorId(user.getId());
                        comment.setPost(parentComment.getPost());
                        comment.setPostId(parentComment.getPostId());
                        comment.setCreatedAt(Instant.now());
                        comment.setUpdatedAt(Instant.now());
        
                        return saveComment(user, comment);
                    })
                )
            );
    }
    
    private Mono<Comment> saveComment(User user, Comment comment) {
        if (comment.getCompanyId() == null) {
            return commentRepository.save(comment);
        } else {
            return companyRepository.findByAdminAndId(user.getId(), comment.getCompanyId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN)))
                .flatMap(company -> {
                    comment.setCompany(company);
                    comment.setCompanyId(company.getId());
            
                    return commentRepository.save(comment);
                });
        }
    }
    
    @Transactional
    public Mono<Comment> updateComment(String userId, Long postId, Long commentId, CommentDTO commentDTO) {
        return findPost(postId)
            .flatMap(found -> commentRepository.findByIdAndAuthor(commentId, userId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "comment", "idnotfound")))
                .flatMap(comment -> {
                    Comment update = mapper.commentDTOToComment(commentDTO);
                    
                    if (update.getContent() != null) {
                        comment.setContent(update.getContent());
                        comment.setUpdatedAt(Instant.now());
                    }
                    if (update.getCompanyId() != null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                    }
                    
                    return commentRepository.save(comment);
                })
            );
    }
    
    @Transactional
    public Mono<Void> deleteComment(String userId, Long postId, Long commentId) {
        return findPost(postId)
            .flatMap(found -> commentRepository.findByIdAndAuthor(commentId, userId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound")))
                .flatMap(commentRepository::delete)
            );
    }
    
    private Mono<Post> findPost(Long postId) {
        return postRepository.findById(postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "post", "idnotfound")));
    }
    
    @Transactional
    public Mono<Attachment> uploadCommentImage(Long postId, Long commentId, byte[] bytes) {
        if (bytes.length == 0) {
            return Mono.empty();
        }
        
        return commentRepository.findByIdAndPost(commentId, postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound")))
            .map(comment -> {
                try {
                    return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                        "public_id", UPLOAD_FOLDER + "/attachments/comments/" + commentId + "-" + postId + "/" + Instant.now().getEpochSecond(),
                        "overwrite", true,
                        "format", "png",
                        "resource_type", "auto",
                        "tags", List.of("comment_attachment")
                    ));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            })
            .flatMap(response -> {
                String thumbUrl = response.get("secure_url").toString();
                Attachment attachment = Attachment.builder()
                    .postId(postId)
                    .commentId(commentId)
                    .createdAt(Instant.now())
                    .thumbUrl(thumbUrl)
                    .build();
    
                return attachmentRepository.save(attachment);
            });
    }
}
