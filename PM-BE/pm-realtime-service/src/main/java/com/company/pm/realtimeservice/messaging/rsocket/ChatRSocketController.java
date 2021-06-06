package com.company.pm.realtimeservice.messaging.rsocket;

import com.company.pm.chatservice.domain.services.ConversationService;
import com.company.pm.chatservice.domain.services.MessageService;
import com.company.pm.chatservice.domain.services.ParticipantService;
import com.company.pm.domain.chatservice.Message;
import com.company.pm.realtimeservice.messaging.rsocket.handler.ChatRSocketHandler;
import com.company.pm.realtimeservice.messaging.rsocket.payload.MessagePayload;
import com.company.pm.realtimeservice.messaging.rsocket.payload.ParticipantPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRSocketController {

    private final ChatRSocketHandler chatHandler;
    
    private final MessageService messageService;
    
    private final ConversationService conversationService;
    
    private final ParticipantService participantService;
    
    private static final Map<String, RSocketRequester> requesterMap = new HashMap<>();
    
    private final Sinks.Many<Message> sink = Sinks.many().replay().limit(Duration.ofMillis(1000));
    
    @ConnectMapping("user-id")
    public void onConnect(
        @Payload String userId,
        RSocketRequester rSocketRequester
    ) {
        rSocketRequester.rsocket().onClose()
            .doFirst(() -> {
                addRequester(userId, rSocketRequester);
            })
            .doOnError((throwable) -> {
                log.warn("Channel to user {} closed", userId);
            })
            .doFinally((consumer) -> {
                removeRequester(userId);
                log.debug("User {} just disconnected", userId);
            })
            .subscribe();
    }
    
    private void addRequester(String userId, RSocketRequester requester) {
        log.debug("Adding user: {}", userId);
        requesterMap.put(userId, requester);
    }
    
    private void removeRequester(String userId) {
        log.debug("Removing user: {}", userId);
        requesterMap.remove(userId);
    }
    
    @MessageMapping("join.channels.{conversationId}")
    public Mono<Void> joinConversation(
        @DestinationVariable("conversationId") Long conversationId,
        @Payload ParticipantPayload payload
    ) {
        return participantService
            .createParticipantByUser(payload.getUserId(), payload.getParticipantId(), conversationId)
            .then();
    }
    
    @MessageMapping("channels.{conversationId}")
    public Mono<Void> handleMessage(
        @DestinationVariable("conversationId") Long conversationId,
        @Payload MessagePayload payload
    ) {
        log.debug("Received message: {}", payload);
        log.debug("Broadcasting to conversation: {}", conversationId);
        
        return chatHandler.handleReceivedMessageReactively(conversationId, payload)
            .doOnSuccess(message -> {
                sink.emitNext(message, (type, result) -> {
                    log.error("Result: {}, {}", type, result);
                    return false;
                });
            })
            .then();
    }
    
    @MessageMapping("channels.{conversationId}")
    public Flux<Message> stream(
        @DestinationVariable("conversationId") Long conversationId,
        @Payload String userId
    ) {
        log.debug("Conversation: {}", conversationId);
        
        return sink.asFlux().share();
    }
}
