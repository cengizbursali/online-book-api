package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.entity.enums.OrderStatus;
import com.getir.onlinebookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderDetailConverter {

    private final BookService bookService;

    public List<OrderDetail> convertAsList(Map<Integer, Integer> bookMap) {
        final List<OrderDetail> orderDetails = new ArrayList<>();
        bookMap.forEach((bookId, quantity) -> orderDetails.add(build(bookId, quantity)));
        return orderDetails;
    }

    private OrderDetail build(Integer bookId, Integer quantity) {
        return OrderDetail.builder()
                .bookId(bookId)
                .orderStatus(OrderStatus.AWAITING_APPROVAL)
                .price(bookService.getById(bookId).getPrice().multiply(BigDecimal.valueOf(quantity)))
                .quantity(quantity)
                .build();
    }
}
