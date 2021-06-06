package com.company.pm.interactionservice.domain.services.dto;

import com.company.pm.interactionservice.domain.services.validators.ValidVisionable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class PostDTO {
    
    @NotNull
    @Size(min = 1)
    private final String content;
    
    @ValidVisionable
    private final String visionable;
}

