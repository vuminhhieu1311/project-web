package com.company.pm.common.validators;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<ValidNumber, String> {
    
    @Override
    public void initialize(ValidNumber constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        return NumberUtils.isDigits(number);
    }
}
