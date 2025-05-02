package com.inghub.wallet.validation;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Scope("prototype")
@AllArgsConstructor
public class UniqueTcknValidator implements ConstraintValidator<UniqueTckn, String> {

    private CustomerRepository customerRepository;


    @Override
    public void initialize(UniqueTckn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.hasText(value) || !customerRepository.existsByTckn(value);
    }
}
