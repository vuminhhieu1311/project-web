package com.company.pm.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumber {
    
    String message() default "Invalid number";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
