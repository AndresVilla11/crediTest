package com.credibanco.Test.validator;

import com.credibanco.Test.validator.impl.GreaterThanZeroValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = GreaterThanZeroValidator.class)
@Documented
public @interface GreaterThanZero {
    String message() default "El valor debe ser mayor que cero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
