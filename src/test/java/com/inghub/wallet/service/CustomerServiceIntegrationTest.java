package com.inghub.wallet.service;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }


    @Test
    void whenCreateCustomer_thenCustomerIsCreated() {
        // given
        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setName("John");
        customer.setSurname("Doe");
        // when
        Customer createdCustomer = customerService.createCustomer(customer);

        // then
        assertEquals(customer.getTckn(), createdCustomer.getTckn());
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getSurname(), createdCustomer.getSurname());
    }

    @Test
    void whenFindCustomerByTckn_thenCustomerIsFound() throws ResultNotFoundException {
        // given
        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setName("John");
        customer.setSurname("Doe");
        customerRepository.save(customer);

        // when
        Customer foundCustomer = customerService.getCustomerByTckn("12345678901");

        // then
        assertEquals(customer.getTckn(), foundCustomer.getTckn());
        assertEquals(customer.getName(), foundCustomer.getName());
        assertEquals(customer.getSurname(), foundCustomer.getSurname());
    }

    @Test
    void whenFindCustomerByTckn_thenThrowException() {
        // given
        // No customer added for this tckn

        // when, then
        assertThrows(ResultNotFoundException.class, () -> customerService.getCustomerByTckn("12345678901"));
    }

    @Test
    void whenFindCustomersWithFilters_thenCorrectPageReturned() {
        // given
        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setName("John");
        customer.setSurname("Doe");
        customerRepository.save(customer);

        // when
        Page<Customer> result = customerService.findCustomers("John", "Doe", "12345678901", 0, 10);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals(customer.getTckn(), result.getContent().get(0).getTckn());
        assertEquals(customer.getName(), result.getContent().get(0).getName());
        assertEquals(customer.getSurname(), result.getContent().get(0).getSurname());
    }

    @Test
    void whenFindCustomersWithFilters_thenCorrectPageReturnedWithDifferentFilters() {
        // given
        Customer customer = new Customer();
        customer.setTckn("98765432101");
        customer.setName("Jane");
        customer.setSurname("Doe");
        customerRepository.save(customer);

        // when
        Page<Customer> result = customerService.findCustomers(null, "Doe", null, 0, 10);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals(customer.getTckn(), result.getContent().get(0).getTckn());
        assertEquals(customer.getName(), result.getContent().get(0).getName());
        assertEquals(customer.getSurname(), result.getContent().get(0).getSurname());
    }
}
