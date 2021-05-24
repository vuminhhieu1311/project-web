package com.company.pm.chatservice.messaging.websocket.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Command {
    
    private final String command;
    
    @Override
    public String toString() {
        return "{\"command\": " + "\"" + command + "\"}";
    }
}
