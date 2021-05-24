package com.company.pm.personalservice.domain.services.dto;

import com.company.pm.common.config.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class ProjectDTO {
    
    @NotNull
    @Size(min = 1)
    private final String name;
    
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String startMonth;
    
    private final String startYear;
    
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String endMonth;
    
    private final String endYear;
    
    private final String creators;
    
    private final String description;
    
    private final String url;
}
