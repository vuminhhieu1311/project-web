package com.company.pm.chatservice.web;

import com.company.pm.chatservice.domain.services.ParticipantService;
import com.company.pm.chatservice.domain.services.dto.ParticipantDTO;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.chatservice.Participant;
import com.company.pm.userservice.domain.assembler.PublicUserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.UserService;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
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
    path = "/api/v1/conversations/{id}/participants",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class ParticipantController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "participant";
    
    private final PublicUserRepresentationModelAssembler assembler;
    
    private final ParticipantService participantService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<UserDTO>>> getParticipants(
        @PathVariable("id") Long conversationId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<UserDTO> participantFlux = participantService.getParticipantsByUser(user.getId(), conversationId);
                            
                            return assembler.toCollectionModel(participantFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping
    public Mono<ResponseEntity<Participant>> createParticipant(
        @PathVariable("id") Long conversationId,
        @Valid ParticipantDTO participantDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> participantService.createParticipantByUser(user.getId(), participantDTO.getParticipantId(), conversationId)
                            .map(participant -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, participant.getId().toString()))
                                .body(participant)
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{participantId}")
    public Mono<ResponseEntity<Void>> deleteParticipant(
        @PathVariable("id") Long conversationId,
        @PathVariable("participantId") String participantId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> participantService.deleteParticipantByUser(user.getId(), participantId, conversationId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, participantId))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
