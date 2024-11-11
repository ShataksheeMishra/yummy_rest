package com.shatakshee.yummyrest.service;
import com.shatakshee.yummyrest.dto.CustomerRequest;

import com.shatakshee.yummyrest.entity.Customer;
import com.shatakshee.yummyrest.mapper.CustomerMapper;
import com.shatakshee.yummyrest.repo.CustomerRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        repo.save(customer);
        return "Created";
    }
}
