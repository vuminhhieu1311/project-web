package com.company.pm.personalservice.domain.services.validators;

import com.company.pm.common.enumeration.EmploymentType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmploymentTypeValidator implements ConstraintValidator<ValidEmploymentType, String> {
    
    @Override
    public void initialize(ValidEmploymentType constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String employmentType, ConstraintValidatorContext context) {
        return employmentType != null && EnumUtils.isValidEnum(EmploymentType.class, employmentType);
    }
}
