package com.company.pm.personalservice.domain.services.dto;

import com.company.pm.common.config.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class PersonalProfileDTO {
    
    private final String headline;
    
    @NotNull
    @Size(min = 1)
    private final String country;
    
    @NotNull
    @Size(min = 1)
    private final String location;
    
    private final String industry;
    
    @Pattern(regexp = Constants.DATE_REGEX)
    private final String birthday;
    
    @Pattern(regexp = Constants.PHONE_REGEX)
    private final String phoneNumber;
    
    private final String address;
    
    private final String about;
    
    private final String bgImageUrl;
}
