package com.company.pm.companyservice.domain.services.validators;

import com.company.pm.common.enumeration.CompanyType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyTypeValidator implements ConstraintValidator<ValidCompanyType, String> {
    
    @Override
    public void initialize(ValidCompanyType constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String companyType, ConstraintValidatorContext context) {
        return companyType != null && EnumUtils.isValidEnum(CompanyType.class, companyType);
    }
}
