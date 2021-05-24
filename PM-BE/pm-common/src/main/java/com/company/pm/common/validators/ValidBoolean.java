package com.company.pm.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BooleanValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBoolean {
    
    String message() default "Invalid boolean";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
