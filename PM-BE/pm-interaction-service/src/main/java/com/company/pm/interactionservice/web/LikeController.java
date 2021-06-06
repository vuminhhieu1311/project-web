package com.company.pm.interactionservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.Like;
import com.company.pm.interactionservice.domain.assembler.LikeRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.LikeService;
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
    path = "/api/v1/posts/{id}",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class LikeController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "like";
    
    private final LikeRepresentationModelAssembler assembler;
    
    private final LikeService likeService;
    
    private final UserService userService;
    
    @GetMapping(path = "/likes")
    public Mono<CollectionModel<EntityModel<Like>>> getUserLikes(
        @PathVariable("id") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Like> likeFlux = likeService.getUsersLikePost(postId);
        
        return assembler.toCollectionModel(likeFlux, exchange);
    }
    
    @GetMapping(path = "/likes/count")
    public Mono<Long> countLike(@PathVariable("id") Long postId) {
        return likeService.countUsersLikePost(postId);
    }
    
    @PostMapping(path = "/action/like")
    public Mono<ResponseEntity<EntityModel<Like>>> likePost(
        @PathVariable("id") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> likeService.likePostByUser(user.getId(), postId)
                            .flatMap(like -> assembler
                                .toModel(like, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, like.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/action/unlike")
    public Mono<ResponseEntity<Void>> unlikePost(
        @PathVariable("id") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> likeService.unlikePostByUser(user.getId(), postId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createAlert(applicationName, "Unlike post", postId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
