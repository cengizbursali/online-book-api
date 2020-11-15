package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDetailDtoConverter {

    private final BookDtoConverter bookDtoConverter;

    public OrderDetailDto convert(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrderId())
                .orderStatus(orderDetail.getOrderStatus())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .bookDto(bookDtoConverter.convert(orderDetail.getBook()))
                .build();
    }
}
