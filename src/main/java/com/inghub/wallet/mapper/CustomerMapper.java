package com.inghub.wallet.mapper;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.model.CustomerCreate;
import com.inghub.wallet.model.CustomerResponse;

public final class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerCreate customerCreate) {
        Customer entity = new Customer();
        entity.setName(customerCreate.getName());
        entity.setSurname(customerCreate.getSurname());
        entity.setTckn(customerCreate.getTckn());
        return entity;
    }

    public static CustomerResponse toModel(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setName(customer.getName());
        response.setSurname(customer.getSurname());
        response.setTckn(customer.getTckn());
        return response;
    }
}
