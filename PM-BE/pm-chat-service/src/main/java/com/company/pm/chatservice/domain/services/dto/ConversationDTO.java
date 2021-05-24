package com.company.pm.chatservice.domain.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class ConversationDTO {
    
    @NotBlank
    @Size(min = 1)
    private final String title;
}
