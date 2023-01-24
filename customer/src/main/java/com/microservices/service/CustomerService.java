package com.microservices.service;

import com.microservices.CustomerRegistrationRequest;
import com.microservices.model.FraudCheckResponse;
import com.microservices.repository.CustomerRepository;
import com.microservices.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse response = restTemplate.getForObject(
            "http://localhost:8081/api/v1/fraud-check/{customerId}",
            FraudCheckResponse.class,
            customer.getId()
        );
        if (response.isFraudster()) {
            throw new IndexOutOfBoundsException("Fraud detected");
        }
    }
}
