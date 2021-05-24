package com.company.pm.socialservice.domain.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class FollowDTO {
    
    @NotNull
    @Size(min = 1)
    private final String followedId;
}
