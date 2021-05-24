package com.company.pm.personalservice.domain.services.dto;

import com.company.pm.common.config.Constants;
import com.company.pm.personalservice.domain.services.validators.ValidEmploymentType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class WorkExperienceDTO {
    
    @NotNull
    @Size(min = 1)
    private final String title;
    
    @ValidEmploymentType
    private final String employmentType;
    
    @NotNull
    @Size(min = 1)
    private final String company;
    
    private final String location;
    
    @NotNull
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String startMonth;
    
    @NotNull
    private final String startYear;
    
    @NotNull
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String endMonth;
    
    @NotNull
    private final String endYear;
    
    private final String industry;
    
    private final String headline;
}
