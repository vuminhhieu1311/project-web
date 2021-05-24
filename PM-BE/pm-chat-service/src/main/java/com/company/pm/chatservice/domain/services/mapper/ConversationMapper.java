package com.company.pm.chatservice.domain.services.mapper;

import com.company.pm.chatservice.domain.services.dto.ConversationDTO;
import com.company.pm.domain.chatservice.Conversation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMapper {
    
    private final ModelMapper mapper;
    
    public Conversation conversationDTOToConversation(ConversationDTO conversationDTO) {
        return mapper.map(conversationDTO, Conversation.class);
    }
}
