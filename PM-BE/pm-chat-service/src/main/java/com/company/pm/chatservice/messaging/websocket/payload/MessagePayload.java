package com.company.pm.chatservice.messaging.websocket.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class MessagePayload {
    
    private final String senderId;
    
    private final String content;
    
    private final Long conversationId;
}
