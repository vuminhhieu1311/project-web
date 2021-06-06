package com.company.pm.interactionservice.web;

import com.company.pm.common.utils.FileUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.interactionservice.domain.assembler.CommentRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.CommentService;
import com.company.pm.interactionservice.domain.services.dto.CommentDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/posts/{id}/comments",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CommentController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "comment";
    
    private final CommentRepresentationModelAssembler assembler;
    
    private final CommentService commentService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Comment>>> getCommentsByPost(
        @PathVariable("id") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Comment> cmtFlux = commentService.getCommentsOfPost(postId);
        
        return assembler.toCollectionModel(cmtFlux, exchange);
    }
    
    @GetMapping(path = "/count")
    public Mono<Long> countCommentsByPost(@PathVariable("id") Long postId) {
        return commentService.countCommentsOfPost(postId);
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<EntityModel<Comment>>> createComment(
        @PathVariable("id") Long postId,
        @RequestPart("image") Mono<FilePart> file,
        @RequestPart("commentDTO") @Valid CommentDTO commentDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentService.createCommentToPost(user.getId(), postId, commentDTO)
                            .flatMap(comment -> file.ofType(FilePart.class)
                                .flatMap(FileUtils::readBytesContent)
                                .flatMap(bytes -> commentService.uploadCommentImage(postId, comment.getId(), bytes))
                                .thenReturn(comment)
                            )
                            .flatMap(comment -> assembler
                                .toModel(comment, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, comment.getId().toString()))
                                    .body(model)
                            ))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(
        path = "/{commentId}/reply",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Comment>>> replyComment(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        @RequestPart("image") Mono<FilePart> file,
        @RequestPart("commentDTO") @Valid CommentDTO commentDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentService.createReplyToComment(user.getId(), postId, commentId, commentDTO)
                            .flatMap(reply -> file.ofType(FilePart.class)
                                .flatMap(FileUtils::readBytesContent)
                                .flatMap(bytes -> commentService.uploadCommentImage(postId, commentId, bytes))
                                .thenReturn(reply)
                            )
                            .flatMap(reply -> assembler
                                .toModel(reply, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reply.getId().toString()))
                                    .body(model)
                            ))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PatchMapping(
        path = "/{commentId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Comment>>> updateComment(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        CommentDTO commentDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentService.updateComment(user.getId(), postId, commentId, commentDTO)
                            .flatMap(comment -> assembler
                                .toModel(comment, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, comment.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{commentId}")
    public Mono<ResponseEntity<Void>> deleteComment(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentService.deleteComment(user.getId(), postId, commentId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, commentId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }

    @GetMapping(path = "/comments/{commentId}/replies/count")
    public Mono<Long> countReply(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long cmtId
    ) {
        return commentService.countRepliesOfComment(postId, cmtId);
    }
    
    @GetMapping(path = "/{commentId}/replies")
    public Mono<CollectionModel<EntityModel<Comment>>> getRepliesOfComment(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long cmtId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Comment> cmtFlux = commentService.getRepliesOfComment(postId, cmtId);
        
        return assembler.toCollectionModel(cmtFlux, exchange);
    }
}
