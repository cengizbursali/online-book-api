package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Customer;
import com.getir.onlinebookapi.model.request.CreateCustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer convert(CreateCustomerRequest createCustomerRequest) {
        return Customer.builder()
                .name(createCustomerRequest.getName())
                .surname(createCustomerRequest.getSurname())
                .age(createCustomerRequest.getAge())
                .email(createCustomerRequest.getEmail())
                .build();
    }
}
