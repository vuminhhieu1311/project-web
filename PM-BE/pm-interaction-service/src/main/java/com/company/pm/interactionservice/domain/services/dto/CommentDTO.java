package com.company.pm.interactionservice.domain.services.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class CommentDTO {
    
    @NotNull
    @Size(min = 1)
    private final String content;
    
    private final String companyId;
}

