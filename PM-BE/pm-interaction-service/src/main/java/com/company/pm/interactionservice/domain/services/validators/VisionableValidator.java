package com.company.pm.interactionservice.domain.services.validators;

import com.company.pm.common.enumeration.Visionable;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VisionableValidator implements ConstraintValidator<ValidVisionable, String> {
    
    @Override
    public void initialize(ValidVisionable constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String visionable, ConstraintValidatorContext context) {
        return visionable != null && EnumUtils.isValidEnum(Visionable.class, visionable);
    }
}
