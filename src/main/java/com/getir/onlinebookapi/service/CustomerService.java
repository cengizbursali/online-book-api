package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.CustomerConverter;
import com.getir.onlinebookapi.entity.Customer;
import com.getir.onlinebookapi.model.request.CreateCustomerRequest;
import com.getir.onlinebookapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;

    public void create(CreateCustomerRequest createCustomerRequest) {
        final Customer customer = customerConverter.convert(createCustomerRequest);
        customerRepository.save(customer);
        log.info("Customer saved to database: {}", customer);
    }
}
