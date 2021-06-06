package com.company.pm.realtimeservice.messaging.rsocket.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class ParticipantPayload {
    
    private final String userId;
    
    private final String participantId;
}
