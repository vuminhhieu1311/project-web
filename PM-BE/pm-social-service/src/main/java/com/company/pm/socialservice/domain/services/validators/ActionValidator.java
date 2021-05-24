package com.company.pm.socialservice.domain.services.validators;

import com.company.pm.common.enumeration.RelAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ActionValidator implements ConstraintValidator<ValidAction, String> {
    
    @Override
    public void initialize(ValidAction constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String action, ConstraintValidatorContext context) {
        return action != null && EnumUtils.isValidEnum(RelAction.class, action);
    }
}
