package com.company.pm.interactionservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.CommentLike;
import com.company.pm.interactionservice.domain.assembler.CommentLikeRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.CommentLikeService;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping(
    path = "/api/v1/posts/{id}/comments/{commentId}",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CommentLikeController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "comment_likes";
    
    private final CommentLikeRepresentationModelAssembler assembler;
    
    private final CommentLikeService commentLikeService;
    
    private final UserService userService;
    
    @GetMapping(path = "/likes")
    public Mono<CollectionModel<EntityModel<CommentLike>>> getCommentLikes(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<CommentLike> commentLikeFlux = commentLikeService.getUsersLikeCommentOfPost(postId, commentId);
        
        return assembler.toCollectionModel(commentLikeFlux, exchange);
    }
    
    @GetMapping(path = "/likes/count")
    public Mono<Long> countCommentLikes(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId
    ) {
        return commentLikeService.countUsersLikeCommentOfPost(postId, commentId);
    }
    
    @PostMapping(path = "/actions/like")
    public Mono<ResponseEntity<EntityModel<CommentLike>>> likeCommentOfPost(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentLikeService.likeCommentByUser(user.getId(), postId, commentId)
                            .flatMap(commentLike -> assembler
                                .toModel(commentLike, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, commentLike.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/actions/unlike")
    public Mono<ResponseEntity<Void>> unlikeCommentOfPost(
        @PathVariable("id") Long postId,
        @PathVariable("commentId") Long commentId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> commentLikeService.unlikeCommentByUser(user.getId(), postId, commentId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createAlert(applicationName, "Unlike comment", commentId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
