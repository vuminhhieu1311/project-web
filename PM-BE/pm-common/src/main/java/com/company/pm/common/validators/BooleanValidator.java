package com.company.pm.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BooleanValidator implements ConstraintValidator<ValidBoolean, String> {
    
    @Override
    public void initialize(ValidBoolean constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String bool, ConstraintValidatorContext context) {
        return bool != null && (bool.equals("true") || bool.equals("false"));
    }
}
