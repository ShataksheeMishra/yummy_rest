package com.shatakshee.yummyrest.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.shatakshee.yummyrest.dto.CustomerRequest;

import com.shatakshee.yummyrest.dto.CustomerResponse;
import com.shatakshee.yummyrest.dto.LoginRequest;
import com.shatakshee.yummyrest.entity.Customer;
import com.shatakshee.yummyrest.exception.CustomerNotFoundException;
import com.shatakshee.yummyrest.filter.JWTFilter;
import com.shatakshee.yummyrest.helper.CustomUserDetails;
import com.shatakshee.yummyrest.helper.JWTHelper;
import com.shatakshee.yummyrest.mapper.CustomerMapper;
import com.shatakshee.yummyrest.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static java.lang.String.format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    public String createCustomer(CustomerRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.password());
        Customer customer = mapper.toEntity(request,encryptedPassword);
        repo.save(customer);
        return "Created";
    }

    public Customer getCustomer(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update Customer:: No customer found with the provided ID:: %s", email)
                ));
    }
    public String login(LoginRequest request) {
        Customer customer = getCustomer(request.email());
        boolean matches = passwordEncoder.matches(request.password(), customer.getPassword());
        if(!matches){
            return "Wrong Password or Email";
        }
        return jwtHelper.generateToken(request.email());

    }

    public CustomerResponse retrieveCustomer(String email) {
        Customer customer = getCustomer(email);
        return mapper.toCustomerResponse(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user details for username: {}", username);
        Customer customer = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + username));
        return new CustomUserDetails(customer); // Wrapping Customer in CustomUserDetails
    }
    public String updateCustomer(JsonNode requestBody) {
        String email = requestBody.get("email").asText();
        Customer existingCustomer = getCustomer(email);

        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        // Update fields if they exist in the request
        if (requestBody.has("firstName")) {
            existingCustomer.setFirstName(requestBody.get("firstName").asText());
        }
        if (requestBody.has("lastName")) {
            existingCustomer.setLastName(requestBody.get("lastName").asText());
        }
//        if (requestBody.has("password")) {
//            existingCustomer.setPassword(passwordEncoder.encode(requestBody.get("password").asText()));
//            existingCustomer.setPass(requestBody.get("password").asText());
//            logger.debug("New Password set {}", existingCustomer.getPass());
//        }

        repo.save(existingCustomer);
        return "Customer updated successfully";
    }

    public void deleteCustomer(String email) {
        Customer existingCustomer = getCustomer(email);
        logger.debug("Loading user details for deletion: {}", existingCustomer);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        // Delete the customer from the repository
        repo.delete(existingCustomer);
    }
}