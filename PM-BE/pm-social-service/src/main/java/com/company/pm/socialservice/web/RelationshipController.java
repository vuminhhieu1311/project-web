package com.company.pm.socialservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.socialservice.Relationship;
import com.company.pm.socialservice.domain.services.RelationshipService;
import com.company.pm.socialservice.domain.services.dto.RelationshipDTO;
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
    path = "/api/v1/relationship",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class RelationshipController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "relationship";
    
    private final PublicUserRepresentationModelAssembler userAssembler;
    
    private final RelationshipService relationshipService;
    
    private final UserService userService;
    
    @GetMapping(
        path = "/friends",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getFriends(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> friendsFlux = relationshipService.getFriendsByUser(user.getId());
                            
                            return userAssembler.toCollectionModel(friendsFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(
        path = "/pending",
        produces = MediaTypes.HAL_JSON_VALUE
    )
    public Mono<CollectionModel<EntityModel<UserDTO>>> getPendingRequest(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> pendingFlux = relationshipService.getPendingRequestByUser(user.getId());
                            
                            return userAssembler.toCollectionModel(pendingFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/users/{anotherId}")
    public Mono<Relationship> getRel(
        @PathVariable("anotherId") String anotherId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> relationshipService.getRelationship(user.getId(), anotherId));
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<Relationship>> createOrUpdateRel(
        @Valid RelationshipDTO relationshipDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> relationshipService.createOrUpdateRelationship(user.getId(), relationshipDTO)
                            .map(relationship -> ResponseEntity
                                .ok()
                                .headers(HeaderUtil.createAlert(applicationName, "A " + ENTITY_NAME +
                                    " is changed with identifier " + relationship.getId(), relationship.getId().toString()))
                                .body(relationship)
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
