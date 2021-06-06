package com.company.pm.interactionservice.domain.services.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VisionableValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVisionable {
    
    String message() default "Invalid visionable";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
