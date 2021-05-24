package com.company.pm.personalservice.domain.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class EducationDTO {
    
    @NotNull
    @Size(min = 1)
    private final String school;
    
    private final String degree;
    
    private final String fieldOfStudy;
    
    private final String startYear;
    
    private final String endYear;
    
    private final String grade;
    
    private final String activities;
    
    private final String description;
}
