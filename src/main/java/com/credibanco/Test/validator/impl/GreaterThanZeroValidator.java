package com.credibanco.Test.validator.impl;

import com.credibanco.Test.validator.GreaterThanZero;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GreaterThanZeroValidator implements ConstraintValidator<GreaterThanZero, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int parseInt = value.isEmpty() ? -1 : Integer.parseInt(value);
        return parseInt > 0;
    }
}
