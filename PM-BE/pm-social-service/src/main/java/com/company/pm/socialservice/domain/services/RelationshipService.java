package com.company.pm.socialservice.domain.services;

import com.company.pm.common.enumeration.RelAction;
import com.company.pm.common.enumeration.RelStatus;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.socialservice.Follow;
import com.company.pm.domain.socialservice.Relationship;
import com.company.pm.socialservice.domain.repositories.FollowRepository;
import com.company.pm.socialservice.domain.repositories.RelationshipRepository;
import com.company.pm.socialservice.domain.services.dto.RelationshipDTO;
import com.company.pm.socialservice.domain.services.mapper.RelationshipMapper;
import com.company.pm.userservice.domain.repositories.UserRepository;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelationshipService {

    private static final String ENTITY_NAME = "relationship";
    
    private final RelationshipMapper mapper;
    
    private final RelationshipRepository relationshipRepository;
    
    private final FollowRepository followRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<UserDTO> getFriendsByUser(String userId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMapMany(user -> relationshipRepository.findFriendsByUser(userId)
                .flatMap(relationship -> relToUserDTO(user.getId(), relationship))
            );
    }
    
    @Transactional(readOnly = true)
    public Flux<UserDTO> getPendingRequestByUser(String userId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMapMany(user -> relationshipRepository.findPendingRequestByUser(userId)
                .flatMap(relationship -> relToUserDTO(user.getId(), relationship))
            );
    }
    
    private Mono<UserDTO> relToUserDTO(String userId, Relationship relationship) {
        if (relationship.getRequesterId().equals(userId)) {
            return userRepository.findById(relationship.getAddresseeId())
                .map(friend -> new UserDTO(
                    friend.getId(), friend.getLogin(),
                    friend.getFirstName(), friend.getLastName(),
                    friend.getImageUrl())
                );
        } else if (relationship.getAddresseeId().equals(userId)) {
            return userRepository.findById(relationship.getRequesterId())
                .map(friend -> new UserDTO(
                    friend.getId(), friend.getLogin(),
                    friend.getFirstName(), friend.getLastName(),
                    friend.getImageUrl())
                );
        } else {
            return Mono.error(new BadRequestAlertException("Invalid request", ENTITY_NAME, "requestinvalid"));
        }
    }
    
    @Transactional(readOnly = true)
    public Mono<Relationship> getRelationship(String userId, String anotherUserId) {
        if (userId.equals(anotherUserId)) {
            return Mono.error(new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid"));
        }
        
        return userRepository.findAllById(List.of(userId, anotherUserId))
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entities not found", "user", "idsinvalid")))
            .collectList()
            .flatMap(users -> {
                String requesterId = users.get(0).getId();
                String addresseeId = users.get(1).getId();
                
                return relationshipRepository.findByRequesterIdAndAddresseeId(requesterId, addresseeId);
            });
    }
    
    @Transactional
    public Mono<Relationship> createOrUpdateRelationship(String userId, RelationshipDTO relationshipDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> {
                if (userId.equals(relationshipDTO.getAddresseeId())) {
                    return Mono.error(new BadRequestAlertException("Invalid action", ENTITY_NAME, "actioninvalid"));
                }
                
                Relationship relationship = mapper.relDTOToRel(relationshipDTO);
                relationship.setPerformerId(user.getId());
                relationship.setRequesterId(user.getId());
                
                switch (RelAction.valueOf(relationshipDTO.getAction())) {
                    case ADD:
                        relationship.setStatus(RelStatus.ACCEPTED);
                        break;
                    case SEND:
                        relationship.setStatus(RelStatus.PENDING);
                        break;
                    case REJECT:
                        relationship.setStatus(RelStatus.DECLINED);
                        break;
                    case CANCEL:
                    case UNBLOCK:
                        break;
                    case BLOCK:
                        relationship.setStatus(RelStatus.BLOCKED);
                        break;
                    default:
                }
    
                return relationshipRepository.findByRequesterIdAndAddresseeId(user.getId(), relationship.getAddresseeId())
                    .flatMap(rel -> {
                        if ((relationshipDTO.getAction().equals(RelAction.SEND.toString())
                            && rel.getStatus().equals(RelStatus.DECLINED))
                            || (relationshipDTO.getAction().equals(RelAction.ADD.toString())
                            && rel.getStatus().equals(RelStatus.PENDING))
                            || (relationshipDTO.getAction().equals(RelAction.BLOCK.toString())
                            && !rel.getStatus().equals(RelStatus.BLOCKED))
                            || (relationshipDTO.getAction().equals(RelAction.REJECT.toString())
                            && rel.getStatus().equals(RelStatus.PENDING))
                        ) {
                            rel.setStatus(relationship.getStatus());
                            rel.setPerformerId(user.getId());
                            
                            return relationshipRepository.save(rel)
                                .flatMap(savedRel -> {
                                    if (savedRel.getStatus().equals(RelStatus.ACCEPTED)) {
                                        List<Follow> follows = List.of(
                                            Follow.builder()
                                                .followerId(savedRel.getRequesterId())
                                                .followedId(savedRel.getAddresseeId())
                                                .build(),
                                            Follow.builder()
                                                .followerId(savedRel.getAddresseeId())
                                                .followedId(savedRel.getRequesterId())
                                                .build()
                                        );
                                        
                                        return followRepository.findAllByFollowerIdAndFollowedId(
                                            savedRel.getRequesterId(), savedRel.getRequesterId()
                                        )
                                            .collectList()
                                            .flatMap(followList -> {
                                                List<Follow> result = follows.stream()
                                                    .filter(f -> followList.stream()
                                                        .noneMatch(fl -> fl.getFollowerId().equals(f.getFollowerId())
                                                            && fl.getFollowedId().equals(f.getFollowedId())
                                                        )
                                                    )
                                                    .collect(Collectors.toList());
                                                
                                                if (result.size() > 0) {
                                                    return followRepository.saveAll(result).collectList();
                                                } else {
                                                    return Mono.just(result);
                                                }
                                            })
                                            .switchIfEmpty(followRepository.saveAll(follows).collectList())
                                            .thenReturn(savedRel);
                                    }
                                    if (savedRel.getStatus().equals(RelStatus.BLOCKED)) {
                                        return deleteFollows(savedRel).thenReturn(savedRel);
                                    }
                                    
                                    return Mono.just(savedRel);
                                });
                        } else if ((relationshipDTO.getAction().equals(RelAction.CANCEL.toString())
                            && (rel.getStatus().equals(RelStatus.ACCEPTED)) || rel.getStatus().equals(RelStatus.PENDING))
                            || (relationshipDTO.getAction().equals(RelAction.UNBLOCK.toString())
                            && rel.getStatus().equals(RelStatus.BLOCKED)
                            && user.getId().equals(rel.getPerformerId()))
                        ) {
                            relationship.setId(rel.getId());
                            
                            return relationshipRepository.delete(rel)
                                .thenReturn(relationship);
                        } else {
                            return Mono.error(new BadRequestAlertException("Invalid action", ENTITY_NAME, "actioninvalid"));
                        }
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        if (relationshipDTO.getAction().equals(RelAction.SEND.toString())
                            || relationshipDTO.getAction().equals(RelAction.BLOCK.toString())
                        ) {
                            return relationshipRepository.save(relationship)
                                .flatMap(savedRel -> {
                                    if (savedRel.getStatus().equals(RelStatus.BLOCKED)) {
                                        return deleteFollows(savedRel).thenReturn(savedRel);
                                    }
                                    
                                    return Mono.just(savedRel);
                                });
                        } else {
                            return Mono.error(new BadRequestAlertException("Invalid action", ENTITY_NAME, "actioninvalid"));
                        }
                    }));
            });
    }
    
    private Mono<Void> deleteFollows(Relationship savedRel) {
        return followRepository.findAllByFollowerIdAndFollowedId(
            savedRel.getRequesterId(), savedRel.getAddresseeId()
        )
            .collectList()
            .flatMap(followRepository::deleteAll);
    }
}
