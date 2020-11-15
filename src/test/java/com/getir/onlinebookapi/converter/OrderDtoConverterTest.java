package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.model.response.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderDtoConverterTest {

    @InjectMocks
    private OrderDtoConverter orderDtoConverter;

    @Test
    public void it_should_convert() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Order order = Order.builder()
                .id(3344)
                .price(BigDecimal.TEN)
                .customerId(1)
                .createdDate(now)
                .build();

        // When
        OrderDto orderDto = orderDtoConverter.convert(order);

        // Then
        assertThat(orderDto.getId()).isEqualTo(3344);
        assertThat(orderDto.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(orderDto.getCustomerId()).isEqualTo(1);
        assertThat(orderDto.getCreatedDate()).isEqualTo(now);
    }
}