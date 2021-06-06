package com.company.pm.interactionservice.web;

import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.assembler.PublicCompanyPostRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/companies/{id}/posts",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicCompanyPostController {
    
    private final PublicCompanyPostRepresentationModelAssembler assembler;
    
    private final PostService postService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Post>>> getPublicCompanyPosts(
        @PathVariable("id") Long id,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Post> postFlux = postService.getPublicPostsOfCompany(id);
        
        return assembler.toCollectionModel(postFlux, exchange);
    }
    
    @GetMapping(path = "/{postId}")
    public Mono<EntityModel<Post>> getPublicCompanyPost(
        @PathVariable("id") Long companyId,
        @PathVariable("postId") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return postService.getPublicPostOfCompany(companyId, postId)
            .flatMap(post -> assembler.toModel(post, exchange));
    }
}
