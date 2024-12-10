package org.unibl.etf.springlearning.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SqlValidator.class)
public @interface SqlValidators {
    String message() default "Malicious request registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
