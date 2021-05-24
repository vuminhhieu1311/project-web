package com.company.pm.chatservice.domain.services;

import com.company.pm.chatservice.domain.repositories.MessageRepository;
import com.company.pm.chatservice.domain.repositories.ParticipantRepository;
import com.company.pm.common.enumeration.RelStatus;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.chatservice.Message;
import com.company.pm.domain.chatservice.Participant;
import com.company.pm.socialservice.domain.repositories.RelationshipRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private static final String ENTITY_NAME = "message";
    
    private final MessageRepository messageRepository;
    
    private final ParticipantRepository participantRepository;
    
    private final RelationshipRepository relationshipRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<Message> getChatHistory(String userId, Long conversationId) {
        return participantRepository.findByUserAndConversation(userId, conversationId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "participant", "idnotfound")))
            .map(Participant::getConversation)
            .flatMapMany(conversation -> messageRepository.findByConversation(conversation.getId()));
    }
    
    @Transactional
    public Mono<Message> createMessage(String userId, String content, Long conversationId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> participantRepository.findByUserAndConversation(userId, conversationId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "participant", "idnotfound")))
                .map(Participant::getConversation)
                .flatMap(conversation -> {
                    Message message = Message.builder()
                        .content(content)
                        .createdAt(Instant.now())
                        .deletedAt(Instant.EPOCH)
                        .conversation(conversation)
                        .conversationId(conversation.getId())
                        .sender(user)
                        .senderId(user.getId())
                        .build();
                    
                    return participantRepository.findByConversation(conversation.getId())
                        .collect(Collectors.toSet())
                        .flatMap(participants -> {
                            if (participants.size() > 2) {
                                return messageRepository.save(message);
                            } else if (participants.size() > 0) {
                                List<String> participantsId = participants.stream()
                                    .map(Participant::getUserId)
                                    .collect(Collectors.toList());
                                if (!participantsId.contains(userId)) {
                                    return Mono.error(new BadRequestAlertException("Invalid participant", "participant", "idinvalid"));
                                } else {
                                    return relationshipRepository
                                        .findByRequesterIdAndAddresseeId(participantsId.get(0), participantsId.get(1))
                                        .flatMap(relationship -> {
                                            if (relationship.getStatus().equals(RelStatus.BLOCKED)) {
                                                return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                                            } else {
                                                return messageRepository.save(message);
                                            }
                                        })
                                        .switchIfEmpty(messageRepository.save(message));
                                }
                            } else {
                                return Mono.error(new BadRequestAlertException("Invalid conversation", "conversation", "idinvalid"));
                            }
                        });
                })
            );
    }
}
