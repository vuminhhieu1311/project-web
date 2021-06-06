package com.company.pm.socialservice.web;

import com.company.pm.companyservice.domain.assembler.PublicCompanyRepresentationModelAssembler;
import com.company.pm.domain.companyservice.Company;
import com.company.pm.socialservice.domain.services.FollowService;
import com.company.pm.userservice.domain.assembler.PublicUserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
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
    path = "/api/v1/social",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicFollowController {
    
    private final PublicUserRepresentationModelAssembler userAssembler;
    
    private final PublicCompanyRepresentationModelAssembler companyAssembler;
    
    private final FollowService followService;
    
    @GetMapping(
        path = "/users/{id}/followers",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getPublicFollowersOfUser(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<UserDTO> followerFlux = followService.getFollowersByUser(userId);
        
        return userAssembler.toCollectionModel(followerFlux, exchange);
    }
    
    @GetMapping(
        path = "/users/{id}/followings/companies",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<Company>>> getPublicCompanyFollowingsOfUser(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Company> followingFlux = followService.getFollowingCompanyByUser(userId);
        
        return companyAssembler.toCollectionModel(followingFlux, exchange);
    }
    
    @GetMapping(
        path = "/users/{id}/followings",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getPublicFollowingsOfUser(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<UserDTO> followingFlux = followService.getFollowingsByUser(userId);
        
        return userAssembler.toCollectionModel(followingFlux, exchange);
    }
    
    @GetMapping(path = "/users/{id}/followers/count")
    public Mono<Long> countPublicFollowersOfUser(@PathVariable("id") String userId) {
        return followService.countFollowersByUser(userId);
    }
    
    @GetMapping(path = "/users/{id}/followings/count")
    public Mono<Long> countPublicFollowingsOfUser(@PathVariable("id") String userId) {
        return followService.countFollowingsByUser(userId);
    }
    
    @GetMapping(path = "/companies/{id}/followers/count")
    public Mono<Long> countPublicFollowersOfCompany(@PathVariable("id") Long companyId) {
        return followService.countFollowersOfCompany(companyId);
    }
}
