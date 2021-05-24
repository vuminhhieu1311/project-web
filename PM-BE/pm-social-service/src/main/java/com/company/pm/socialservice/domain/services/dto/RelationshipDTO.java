package com.company.pm.socialservice.domain.services.dto;

import com.company.pm.socialservice.domain.services.validators.ValidAction;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class RelationshipDTO {
    
    @NotNull
    @Size(min = 1)
    private final String addresseeId;
    
    @ValidAction
    private final String action;
}
