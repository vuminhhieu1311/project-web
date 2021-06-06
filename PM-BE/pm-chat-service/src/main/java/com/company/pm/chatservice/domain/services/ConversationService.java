package com.company.pm.chatservice.domain.services;

import com.company.pm.chatservice.domain.repositories.ConversationRepository;
import com.company.pm.chatservice.domain.repositories.ParticipantRepository;
import com.company.pm.chatservice.domain.services.dto.ConversationDTO;
import com.company.pm.chatservice.domain.services.mapper.ConversationMapper;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.chatservice.Conversation;
import com.company.pm.domain.chatservice.Message;
import com.company.pm.domain.chatservice.Participant;
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
public class ConversationService {
    
    private static final String ENTITY_NAME = "conversation";
    
    private final ConversationMapper mapper;
    
    private final ConversationRepository conversationRepository;
    
    private final ParticipantRepository participantRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<Conversation> getConversationsByUser(String userId) {
        return participantRepository.findByUser(userId)
            .map(Participant::getConversation);
    }
    
    @Transactional(readOnly = true)
    public Mono<Conversation> getConversationByUser(String userId, Long conversationId) {
        return participantRepository.findByUserAndConversation(userId, conversationId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(Participant::getConversation);
    }
    
    @Transactional
    public Mono<Conversation> createConversationByUser(String userId, ConversationDTO conversationDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> {
                Conversation conversation = mapper.conversationDTOToConversation(conversationDTO);
                conversation.setCreator(user);
                conversation.setCreatorId(user.getId());
                conversation.setCreatedAt(Instant.now());
                conversation.setUpdatedAt(Instant.now());
                
                return conversationRepository.save(conversation)
                    .flatMap(savedConversation -> {
                        Participant participant = Participant.builder()
                            .conversation(savedConversation)
                            .conversationId(savedConversation.getId())
                            .user(user)
                            .userId(user.getId())
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                        savedConversation.getParticipants().add(participant);
                        
                        return participantRepository.save(participant)
                            .then(conversationRepository.save(savedConversation));
                    });
            });
    }
    
    @Transactional
    public Mono<Conversation> updateConversationByUser(String userId, Long conversationId, Message message) {
        return getConversationByUser(userId, conversationId)
            .flatMap(conversation -> {
                conversation.setUpdatedAt(Instant.now());
                conversation.getMessages().add(message);
                
                return conversationRepository.save(conversation);
            });
    }
    
    @Transactional
    public Mono<Conversation> getOrCreateOneToOneConversation(String userId, String participantId) {
        String id1, id2;
        if (userId.compareTo(participantId) > 0) {
            id1 = userId;
            id2 = participantId;
        } else {
            id1 = participantId;
            id2 = userId;
        }
        
        return conversationRepository.findOneToOneChat(id1, id2)
            .switchIfEmpty(createConversationByUser(userId, new ConversationDTO(id1 + "-" + id2))
                .flatMap(conversation -> {
                    if (userId.compareTo(participantId) > 0) {
                       conversation.setCreatorId(userId + "-" + participantId);
                    } else {
                       conversation.setCreatorId(participantId + "-" + userId);
                    }
                    
                    return conversationRepository.save(conversation);
                })
            );
    }
}
