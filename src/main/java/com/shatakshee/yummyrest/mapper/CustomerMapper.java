package com.shatakshee.yummyrest.mapper;

import com.shatakshee.yummyrest.dto.CustomerRequest;
import com.shatakshee.yummyrest.dto.CustomerResponse;
import com.shatakshee.yummyrest.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toEntity(CustomerRequest request,String encryptedPassword) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encryptedPassword)
                .pass(request.password())
                .build();
    }
    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}