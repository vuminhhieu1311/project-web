package com.company.pm.chatservice.web;

import com.company.pm.chatservice.domain.assembler.MessageRepresentationModelAssembler;
import com.company.pm.chatservice.domain.services.MessageService;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.chatservice.Message;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
    path = "/api/v1/conversations/{id}/message-history",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class MessageController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "message";
    
    private final MessageRepresentationModelAssembler assembler;
    
    private final MessageService messageService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Message>>> getChatHistory(
        @PathVariable("id") Long conversationId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Message> messageFlux = messageService.getChatHistory(user.getId(), conversationId);
                            
                            return assembler.toCollectionModel(messageFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
