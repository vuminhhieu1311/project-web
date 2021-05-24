package com.company.pm.companyservice.domain.services.validators;

import com.company.pm.common.enumeration.JobType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JobTypeValidator implements ConstraintValidator<ValidJobType, String> {
    
    @Override
    public void initialize(ValidJobType constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String jobType, ConstraintValidatorContext context) {
        return jobType != null && EnumUtils.isValidEnum(JobType.class, jobType);
    }
}
