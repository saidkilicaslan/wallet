package com.inghub.wallet.controller;

import com.inghub.wallet.api.CustomerApi;
import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.IllegalRequestParamException;
import com.inghub.wallet.mapper.CustomerMapper;
import com.inghub.wallet.model.CustomerCreate;
import com.inghub.wallet.model.CustomerResponse;
import com.inghub.wallet.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerApiController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerCreate customerCreate) {
        return new ResponseEntity<>(CustomerMapper.toModel(customerService.createCustomer(CustomerMapper.toEntity(customerCreate))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerByTckn(@PathVariable("tckn") String tckn) throws ApiException {
        return new ResponseEntity<>(CustomerMapper.toModel(customerService.getCustomerByTckn(tckn)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> listCustomers(@RequestParam(name = "name", required = false) String name,
                                                                @RequestParam(name = "surname", required = false) String surname,
                                                                @RequestParam(name = "tckn", required = false) String tckn,
                                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size, HttpServletResponse response) throws ApiException {
        if (page < 0 || size <= 0) {
            throw new IllegalRequestParamException("Page number and size must be greater than 0");
        }
        Page<Customer> customerPage = customerService.findCustomers(name, surname, tckn, page, size);
        if (customerPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(customerPage.getTotalPages() > 1){
            response.setHeader("X-Total-Count", String.valueOf(customerPage.getTotalElements()));
            response.setHeader("X-Total-Pages", String.valueOf(customerPage.getTotalPages()));
            response.setHeader("X-Current-Page", String.valueOf(page));
            response.setHeader("X-Page-Size", String.valueOf(size));
            return new ResponseEntity<>(customerPage.getContent().stream().map(CustomerMapper::toModel).toList(), HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(customerPage.getContent().stream().map(CustomerMapper::toModel).toList(), HttpStatus.OK);

    }
}
