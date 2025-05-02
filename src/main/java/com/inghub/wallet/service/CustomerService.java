package com.inghub.wallet.service;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        log.info("Creating customer with TCKN: {}", customer.getTckn());
        return customerRepository.save(customer);
    }

    public Customer getCustomerByTckn(String tckn) throws ResultNotFoundException {
        log.info("Retrieving customer with TCKN: {}", tckn);
        return customerRepository.findByTckn(tckn)
                .orElseThrow(() -> new ResultNotFoundException("Customer not found"));
    }

    public Page<Customer> findCustomers(String name, String surname, String tckn, Integer pageNumber, Integer pageSize) {
        log.info("Searching customers with name: {}, surname: {}, TCKN: {}", name, surname, tckn);
        Customer probe = new Customer();
        probe.setName(name);
        probe.setSurname(surname);
        probe.setTckn(tckn);
        return customerRepository.findAll(Example.of(probe, ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()), Pageable.ofSize(pageSize).withPage(pageNumber));
    }

}
