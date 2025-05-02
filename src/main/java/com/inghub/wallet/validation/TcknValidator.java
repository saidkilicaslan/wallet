package com.inghub.wallet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class TcknValidator implements ConstraintValidator<ValidTckn, String> {
    @Override
    public void initialize(ValidTckn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.hasText(value) || value.matches("^\\d{11}$");
    }
}
