package com.company.pm.socialservice.domain.services.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ActionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAction {
    
    String message() default "Invalid action";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
