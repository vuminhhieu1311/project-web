package com.company.pm.realtimeservice.messaging.rsocket.handler;

import com.company.pm.chatservice.domain.services.ConversationService;
import com.company.pm.chatservice.domain.services.MessageService;
import com.company.pm.realtimeservice.messaging.rsocket.payload.MessagePayload;
import com.company.pm.domain.chatservice.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRSocketHandler {
    
    private final ConversationService conversationService;
    
    private final MessageService messageService;
    
    @Transactional
    public Mono<Message> handleReceivedMessageReactively(Long conversationId, MessagePayload payload) {
        return conversationService.getConversationByUser(payload.getSenderId(), conversationId)
            .flatMap(conversation -> messageService
                .createMessage(payload.getSenderId(), payload.getContent(), conversation.getId())
                .flatMap(savedMessage -> conversationService
                    .updateConversationByUser(payload.getSenderId(), conversation.getId(), savedMessage)
                    .thenReturn(savedMessage)
                )
            );
    }
}
