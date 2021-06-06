package com.company.pm.interactionservice.web;

import com.company.pm.common.utils.FileUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.assembler.CompanyPostRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.PostService;
import com.company.pm.interactionservice.domain.services.dto.PostDTO;
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
    path = "/api/v1/companies/{id}/admin/posts",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CompanyPostController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "post";
    
    private final CompanyPostRepresentationModelAssembler assembler;
    
    private final PostService postService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Post>>> getCompanyPosts(
        @PathVariable("id") Long companyId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Post> postFlux = postService.getPostsOfCompanyByAdmin(user.getId(), companyId);
                            
                            return assembler.toCollectionModel(postFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/{postId}")
    public Mono<EntityModel<Post>> getCompanyPost(
        @PathVariable("id") Long companyId,
        @PathVariable("postId") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> postService.getPostOfCompanyByAdmin(user.getId(), postId, companyId)
                             .flatMap(post -> assembler.toModel(post, exchange))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<EntityModel<Post>>> createCompanyPost(
        @PathVariable("id") Long companyId,
        @RequestPart("image") Mono<FilePart> file,
        @RequestPart("postDTO") @Valid PostDTO postDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> postService.createCompanyPostByUser(user.getId(), companyId, postDTO)
                            .flatMap(post -> file.ofType(FilePart.class)
                                .flatMap(FileUtils::readBytesContent)
                                .flatMap(bytes -> postService.uploadCompanyPostImage(user.getId(), companyId, post.getId(), bytes))
                                .thenReturn(post)
                            )
                            .flatMap(post -> assembler
                                .toModel(post, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, post.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PatchMapping(
        path = "/{postId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Post>>> updateCompanyPost(
        @PathVariable("id") Long companyId,
        @PathVariable("postId") Long postId,
        PostDTO postDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> postService.updateCompanyPostByUser(user.getId(), companyId, postId, postDTO)
                            .flatMap(post -> assembler
                                .toModel(post, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, post.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{postId}")
    public Mono<ResponseEntity<Void>> deleteCompanyPost(
        @PathVariable("id") Long companyId,
        @PathVariable("postId") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> postService.deleteCompanyPostByUser(user.getId(), companyId, postId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, postId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
