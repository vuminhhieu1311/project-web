package com.company.pm.personalservice.domain.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class SkillDTO {
    
    @NotNull
    @Size(min = 1)
    private final String name;
    
    private final String category;
}
