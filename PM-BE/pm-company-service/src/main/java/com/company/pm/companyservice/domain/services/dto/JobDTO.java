package com.company.pm.companyservice.domain.services.dto;

import com.company.pm.common.validators.ValidBoolean;
import com.company.pm.companyservice.domain.services.validators.ValidJobType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class JobDTO {

    @NotNull
    @Size(min = 1)
    private final String title;
    
    @NotNull
    @Size(min = 1)
    private final String company;
    
    @NotNull
    @Size(min = 1)
    private final String location;
    
    @ValidJobType
    private final String jobType;
    
    @NotNull
    @Size(min = 1)
    private final String description;
    
    @Email
    private final String contactEmail;
    
    @ValidBoolean
    private final String activated;
}
