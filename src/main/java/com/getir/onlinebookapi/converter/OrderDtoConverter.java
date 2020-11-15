package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.model.response.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverter {

    public OrderDto convert(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .createdDate(order.getCreatedDate())
                .customerId(order.getCustomerId())
                .price(order.getPrice())
                .build();
    }
}
