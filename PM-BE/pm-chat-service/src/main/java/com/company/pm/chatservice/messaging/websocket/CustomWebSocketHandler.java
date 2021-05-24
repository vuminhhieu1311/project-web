package com.company.pm.chatservice.messaging.websocket;

import com.company.pm.chatservice.domain.services.ConversationService;
import com.company.pm.chatservice.domain.services.MessageService;
import com.company.pm.chatservice.messaging.websocket.payload.Command;
import com.company.pm.chatservice.messaging.websocket.payload.MessagePayload;
import com.company.pm.domain.chatservice.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomWebSocketHandler implements WebSocketHandler {
    
    private final ObjectMapper objectMapper;
    
    private final ConversationService conversationService;
    
    private final MessageService messageService;
    
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> output = session.receive()
            .flatMap(webSocketMessage -> {
                String msg = webSocketMessage.getPayloadAsText();
    
                if (checkJsonCompatibility(msg, Command.class)) {
                    try {
                        Command command = objectMapper.readValue(msg, Command.class);
            
                        if (command.getCommand().equals("ping")) {
                            return Mono.just(new Command("pong").toString());
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
    
                if (checkJsonCompatibility(msg, MessagePayload.class)) {
                    try {
                        MessagePayload received = objectMapper.readValue(msg, MessagePayload.class);
                        log.debug("Parsed message: {}", received);
                        
                        return handleReceivedMessageReactively(received)
                            .doOnError(throwable -> {
                                log.error("{}", throwable.getMessage());
                                session.close(CloseStatus.create(
                                    CloseStatus.BAD_DATA.getCode(),
                                    throwable.getMessage())
                                ).subscribe();
                            })
                            .doOnSuccess(message -> {
                                log.debug("Saved message: {}", message);
                            });
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
    
                return Mono.just(new Command("sent").toString());
            })
            .map(serializable -> session.textMessage(serializable.toString()));
        
        return session.send(output);
    }
    
    @Transactional
    public Mono<Message> handleReceivedMessageReactively(MessagePayload payload) {
        return conversationService.getConversationByUser(payload.getSenderId(), payload.getConversationId())
            .flatMap(conversation -> messageService
                .createMessage(payload.getSenderId(), payload.getContent(), conversation.getId())
                .flatMap(savedMessage -> conversationService
                    .updateConversationByUser(payload.getSenderId(), conversation.getId(), savedMessage)
                    .thenReturn(savedMessage)
                )
            );
    }
    
    private boolean checkJsonCompatibility(String jsonStr, Class<?> valueType) {
        try {
            objectMapper.readValue(jsonStr, valueType);
            return true;
        } catch (JsonProcessingException e) {
            log.debug("Payload is not type of {}", valueType);
            return false;
        }
    }
}
