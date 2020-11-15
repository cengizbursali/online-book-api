package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Customer;
import com.getir.onlinebookapi.model.request.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CustomerConverterTest {

    @InjectMocks
    private CustomerConverter customerConverter;

    @Test
    public void it_should_convert() {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .name("cengiz")
                .surname("bursali")
                .age(28)
                .email("cengizbursali@gmail.com")
                .build();

        // When
        Customer customer = customerConverter.convert(createCustomerRequest);

        // Then
        assertThat(customer.getName()).isEqualTo("cengiz");
        assertThat(customer.getSurname()).isEqualTo("bursali");
        assertThat(customer.getAge()).isEqualTo(28);
        assertThat(customer.getEmail()).isEqualTo("cengizbursali@gmail.com");
    }
}