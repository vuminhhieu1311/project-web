package com.company.pm.companyservice.domain.services.dto;

import com.company.pm.common.validators.ValidNumber;
import com.company.pm.companyservice.domain.services.validators.ValidCompanyType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class CompanyDTO {
    
    @NotNull
    @Size(min = 1)
    private final String name;
    
    private final String website;
    
    @NotNull
    @Size(min = 1)
    private final String industry;
    
    @ValidNumber
    private final String companySize;
    
    @ValidCompanyType
    private final String companyType;
    
    private final String logoUrl;
    
    private final String tagline;
    
    private final String bgImageUrl;
}
