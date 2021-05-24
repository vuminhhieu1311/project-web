package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.domain.assembler.PublicationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.PublicationService;
import com.company.pm.personalservice.domain.services.dto.PublicationDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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
    path = "/api/v1/profile/pubs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicationController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "publication";
    
    private final PublicationRepresentationModelAssembler assembler;
    
    private final PublicationService publicationService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Publication>>> getPubs(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Publication> publicationFlux = publicationService.getPubsByProfile(user.getId());
                            
                            return assembler.toCollectionModel(publicationFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/{pubId}")
    public Mono<EntityModel<Publication>> getPub(
        @PathVariable("pubId") Long pubId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> publicationService.getPubByProfile(user.getId(), pubId)
                            .flatMap(publication -> assembler.toModel(publication, exchange))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Publication>>> createPub(
        @Valid PublicationDTO publicationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> publicationService.createPubByProfile(user.getId(), publicationDTO)
                            .flatMap(publication -> assembler
                                .toModel(publication, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, publication.getId().toString()))
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
        path = "/{pubId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Publication>>> updatePub(
        @PathVariable("pubId") Long pubId,
        PublicationDTO publicationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> publicationService.updatePubByProfile(user.getId(), pubId, publicationDTO)
                            .flatMap(publication -> assembler
                                .toModel(publication, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publication.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{pubId}")
    public Mono<ResponseEntity<Void>> deletePub(
        @PathVariable("pubId") Long pubId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> publicationService.deletePubByProfile(user.getId(), pubId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, pubId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
