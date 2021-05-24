package com.company.pm.socialservice.web;

import com.company.pm.socialservice.domain.services.RelationshipService;
import com.company.pm.userservice.domain.assembler.PublicUserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.UserDTO;
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
    path = "/api/v1/users/{id}",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicRelationshipController {
    
    private final PublicUserRepresentationModelAssembler userAssembler;
    
    private final RelationshipService relationshipService;
    
    @GetMapping(path = "/friends")
    public Mono<CollectionModel<EntityModel<UserDTO>>> getPublicFriends(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<UserDTO> userFlux = relationshipService.getFriendsByUser(userId);
        
        return userAssembler.toCollectionModel(userFlux, exchange);
    }
}
