package com.inghub.wallet.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TcknValidatorTest {

    @Mock
    private ValidTckn annotation;

    @Mock
    private ConstraintValidatorContext context;

    private TcknValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new TcknValidator();
        validator.initialize(annotation);
    }

    @Test
    void givenNullTckn_whenValidate_thenReturnTrue() {
        Assertions.assertTrue(validator.isValid(null, context));
    }

    @Test
    void givenEmptyTckn_whenValidate_thenReturnTrue() {
        Assertions.assertTrue(validator.isValid("", context));
    }

    @Test
    void givenTcknContainingLetters_whenValidate_thenReturnFalse() {
        Assertions.assertFalse(validator.isValid("1234567890a", context));
        Assertions.assertFalse(validator.isValid("Acdefg", context));
    }

    @Test
    void givenTcknContainingSpecialCharacters_whenValidate_thenReturnFalse() {
        Assertions.assertFalse(validator.isValid("1234567890!", context));
        Assertions.assertFalse(validator.isValid("@1234!", context));
    }

    @Test
    void givenTcknContainingSpaces_whenValidate_thenReturnFalse() {
        Assertions.assertFalse(validator.isValid("123 456 7890", context));
    }

    @Test
    void givenTcknContainingLessThan11Digits_whenValidate_thenReturnFalse() {
        Assertions.assertFalse(validator.isValid("123456789", context));
    }

    @Test
    void givenTcknContainingMoreThan11Digits_whenValidate_thenReturnFalse() {
        Assertions.assertFalse(validator.isValid("123456789012", context));
    }

    @Test
    void givenValidTckn_whenValidate_thenReturnTrue() {
        Assertions.assertTrue(validator.isValid("12345678901", context));
    }
}
