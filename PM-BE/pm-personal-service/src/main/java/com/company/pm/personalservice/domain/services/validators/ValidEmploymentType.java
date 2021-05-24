package com.company.pm.personalservice.domain.services.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmploymentTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmploymentType {
    
    String message() default "Invalid employment type";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
