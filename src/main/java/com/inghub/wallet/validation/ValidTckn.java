package com.inghub.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {TcknValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidTckn {
    String message() default "TCKN must be exactly 11 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}