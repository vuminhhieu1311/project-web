package com.company.pm.chatservice.domain.services;

import com.company.pm.chatservice.domain.repositories.ConversationRepository;
import com.company.pm.chatservice.domain.repositories.ParticipantRepository;
import com.company.pm.common.enumeration.RelStatus;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.chatservice.Conversation;
import com.company.pm.domain.chatservice.Participant;
import com.company.pm.socialservice.domain.repositories.RelationshipRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    
    private static final String ENTITY_NAME = "participant";
    
    private final ParticipantRepository participantRepository;
    
    private final ConversationRepository conversationRepository;
    
    private final RelationshipRepository relationshipRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<UserDTO> getParticipantsByUser(String userId, Long conversationId) {
        return conversationRepository.findById(conversationId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "conversation", "idnotfound")))
            .flatMapMany(conversation -> participantRepository.findByConversation(conversation.getId())
                .collect(Collectors.toSet())
                .flatMapMany(participants -> {
                    Set<String> participantsId = participants.stream()
                        .map(Participant::getUserId)
                        .collect(Collectors.toSet());

                    if (participantsId.contains(userId)) {
                        return participantRepository.findByConversation(conversation.getId())
                            .map(this::participantToUserDTO);
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                    }
                })
            );
    }
    
    private UserDTO participantToUserDTO(Participant participant) {
        return new UserDTO(
            participant.getUserId(),
            participant.getUser().getLogin(),
            participant.getUser().getFirstName(),
            participant.getUser().getLastName(),
            participant.getUser().getImageUrl()
        );
    }
    
    @Transactional
    public Mono<Participant> createParticipantByUser(String userId, String participantId, Long conversationId) {
        return conversationRepository.findByIdAndCreator(conversationId, userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "conversation", "idnotfound")))
            .flatMap(conversation -> relationshipRepository.findByRequesterIdAndAddresseeId(userId, participantId)
                .flatMap(relationship -> {
                    if (relationship.getStatus().equals(RelStatus.BLOCKED)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                    } else {
                        return saveParticipant(conversation, participantId);
                    }
                })
                .switchIfEmpty(saveParticipant(conversation, participantId))
            );
    }
    
    private Mono<Participant> saveParticipant(Conversation conversation, String participantId) {
        return participantRepository.findByConversation(conversation.getId())
            .collect(Collectors.toSet())
            .flatMap(participants -> {
                Set<String> participantsId = participants.stream()
                    .map(Participant::getUserId)
                    .collect(Collectors.toSet());
            
                if (!participantsId.contains(participantId)) {
                    return userRepository.findById(participantId)
                        .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
                        .flatMap(user -> {
                            Participant participant = Participant.builder()
                                .conversation(conversation)
                                .conversationId(conversation.getId())
                                .user(user)
                                .userId(participantId)
                                .createdAt(Instant.now())
                                .updatedAt(Instant.now())
                                .build();
                        
                            return participantRepository.save(participant);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid action", ENTITY_NAME, "actioninvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deleteParticipantByUser(String userId, String participantId, Long conversationId) {
        return conversationRepository.findByIdAndCreator(conversationId, userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "conversation", "idnotfound")))
            .flatMap(conversation -> participantRepository
                .findByUserAndConversation(participantId, conversation.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(participantRepository::delete)
            );
    }
}
