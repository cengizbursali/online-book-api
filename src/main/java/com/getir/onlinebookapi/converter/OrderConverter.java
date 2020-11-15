package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderDetailConverter orderDetailConverter;

    public Order convert(CreateOrderRequest createOrderRequest) {
        final List<OrderDetail> orderDetails = orderDetailConverter.convertAsList(createOrderRequest.getBookMap());
        final BigDecimal orderPrice = orderDetails.stream()
                .map(OrderDetail::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Order.builder()
                .customerId(createOrderRequest.getCustomerId())
                .price(orderPrice)
                .orderDetails(orderDetails)
                .build();
    }
}
