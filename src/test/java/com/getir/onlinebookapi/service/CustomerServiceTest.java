package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.CustomerConverter;
import com.getir.onlinebookapi.entity.Customer;
import com.getir.onlinebookapi.model.request.CreateCustomerRequest;
import com.getir.onlinebookapi.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerConverter customerConverter;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void it_should_create_customer() {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder().build();
        Customer customer = Customer.builder().build();
        when(customerConverter.convert(createCustomerRequest)).thenReturn(customer);

        // When
        customerService.create(createCustomerRequest);

        // Then
        verify(customerConverter).convert(createCustomerRequest);
        verify(customerRepository).save(customer);
    }
}