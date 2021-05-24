package com.company.pm.personalservice.domain.services.dto;

import com.company.pm.common.config.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class CertificationDTO {
    
    @NotNull
    @Size(min = 1)
    private final String name;
    
    @NotNull
    @Size(min = 1)
    private final String issOrganization;
    
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String issMonth;
    
    private final String issYear;
    
    @Pattern(regexp = Constants.MONTH_REGEX)
    private final String expMonth;
    
    private final String expYear;
    
    private final String credentialID;
    
    private final String credentialURL;
}
