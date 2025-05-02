package com.inghub.wallet.validation;

import com.inghub.wallet.repository.CustomerRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UniqueTcknValidatorTest {

    @Mock
    private UniqueTckn annotation;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private CustomerRepository customerRepository;

    private UniqueTcknValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new UniqueTcknValidator(customerRepository);
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
    void givenTcknAlreadyExists_whenValidate_thenReturnFalse() {
        Mockito.when(customerRepository.existsByTckn("12345678901")).thenReturn(true);
        Assertions.assertFalse(validator.isValid("12345678901", context));
    }

    @Test
    void givenTcknDoesNotExist_whenValidate_thenReturnTrue() {
        Mockito.when(customerRepository.existsByTckn("12345678901")).thenReturn(false);
        Assertions.assertTrue(validator.isValid("12345678901", context));
    }
}
