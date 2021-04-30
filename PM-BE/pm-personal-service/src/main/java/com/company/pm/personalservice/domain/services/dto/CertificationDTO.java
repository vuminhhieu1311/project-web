package com.company.pm.personalservice.domain.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class CertificationDTO {

    @NotNull
    @Length(min = 1)
    private final String name;
}
