package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.OrderConverter;
import com.getir.onlinebookapi.converter.OrderDtoConverter;
import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import com.getir.onlinebookapi.model.request.FilterOrderRequest;
import com.getir.onlinebookapi.model.response.OrderDto;
import com.getir.onlinebookapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final BookStockService bookStockService;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final OrderDtoConverter orderDtoConverter;

    @Transactional
    public void create(CreateOrderRequest createOrderRequest) {
        bookStockService.decrementStock(createOrderRequest.getBookMap());
        final Order order = orderConverter.convert(createOrderRequest);
        orderRepository.save(order);
    }

    public List<OrderDto> getAll(FilterOrderRequest filterOrderRequest) {
        final List<Order> orderList = orderRepository.findAllByCustomerId(filterOrderRequest.getCustomerId());
        return orderList.stream().map(orderDtoConverter::convert).collect(Collectors.toList());
    }
}
