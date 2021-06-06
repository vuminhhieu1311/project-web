package com.company.pm.socialservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.assembler.PublicCompanyRepresentationModelAssembler;
import com.company.pm.domain.companyservice.Company;
import com.company.pm.domain.socialservice.Follow;
import com.company.pm.socialservice.domain.services.FollowService;
import com.company.pm.socialservice.domain.services.dto.FollowDTO;
import com.company.pm.userservice.domain.assembler.PublicUserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.UserService;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    path = "/api/v1/social",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class FollowController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "follow";
    
    private final PublicUserRepresentationModelAssembler userAssembler;
    
    private final PublicCompanyRepresentationModelAssembler companyAssembler;
    
    private final FollowService followService;
    
    private final UserService userService;
    
    @GetMapping(
        path = "/followers",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getFollowersOfUser(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> followerFlux = followService.getFollowersByUser(user.getId());
                            
                            return userAssembler.toCollectionModel(followerFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(
        path = "/followings",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getFollowingsOfUser(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> followingFlux = followService.getFollowingsByUser(user.getId());
                            
                            return userAssembler.toCollectionModel(followingFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(
        path = "/companies/{id}/followers",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getFollowersOfCompany(
        @PathVariable("id") Long companyId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> followerFlux = followService.getFollowersOfCompany(user.getId(), companyId);
                            
                            return userAssembler.toCollectionModel(followerFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(
        path = "/followings/companies",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<Company>>> getFollowingsCompanyOfUser(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Company> followingFlux = followService.getFollowingCompanyByUser(user.getId());
                            
                            return companyAssembler.toCollectionModel(followingFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/followers/count")
    public Mono<Long> countFollowersOfUser(@ApiIgnore ServerWebExchange exchange) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> followService.countFollowersByUser(user.getId()));
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/followings/count")
    public Mono<Long> countFollowingsOfUser(@ApiIgnore ServerWebExchange exchange) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> followService.countFollowingsByUser(user.getId()));
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(
        path = "/actions/follow",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<Follow>> follow(
        @Valid FollowDTO followDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> followService.createFollowByUser(user.getId(), followDTO)
                             .map(follow -> ResponseEntity
                                .ok()
                                .headers(HeaderUtil.createAlert(applicationName, "User with identifier" + user.getId()
                                    + " followed user with identifier " + followDTO.getFollowedId(), follow.getId().toString()))
                                .body(follow)
                             )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/actions/unfollow")
    public Mono<ResponseEntity<Void>> unfollow(
        @RequestParam("followedId") String followedId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> followService.deleteFollowByUser(user.getId(), followedId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createAlert(applicationName, "User with identifier " + user.getId() +
                                    " unfollowed user with identifier " + followedId, ""))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
