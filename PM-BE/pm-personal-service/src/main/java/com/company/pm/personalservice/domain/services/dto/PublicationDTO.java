package com.company.pm.personalservice.domain.services.dto;

import com.company.pm.common.config.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class PublicationDTO {
    
    @NotNull
    @Size(min = 1)
    private final String title;
    
    private final String publisher;
    
    @Pattern(regexp = Constants.DATE_REGEX)
    private final String publicationDate;
    
    private final String authors;
    
    private final String url;
    
    private final String description;
}
