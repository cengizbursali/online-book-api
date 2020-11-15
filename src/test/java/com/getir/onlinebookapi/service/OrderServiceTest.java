package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.OrderConverter;
import com.getir.onlinebookapi.converter.OrderDtoConverter;
import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import com.getir.onlinebookapi.model.request.FilterOrderRequest;
import com.getir.onlinebookapi.model.response.OrderDto;
import com.getir.onlinebookapi.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private BookStockService bookStockService;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDtoConverter orderDtoConverter;

    @Test
    public void it_should_create_order() {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().bookMap(bookMap).build();
        Order order = Order.builder().build();
        when(orderConverter.convert(createOrderRequest)).thenReturn(order);

        // When
        orderService.create(createOrderRequest);

        // Then
        verify(bookStockService).decrementStock(bookMap);
        verify(orderConverter).convert(createOrderRequest);
        verify(orderRepository).save(order);
    }

    @Test
    public void it_should_get_all_order_list() {
        // Given
        FilterOrderRequest filterOrderRequest = new FilterOrderRequest(3344);
        Order order = Order.builder().build();
        when(orderRepository.findAllByCustomerId(3344)).thenReturn(Collections.singletonList(order));
        OrderDto orderDto = OrderDto.builder().build();
        when(orderDtoConverter.convert(order)).thenReturn(orderDto);

        // When
        List<OrderDto> orderDtoList = orderService.getAll(filterOrderRequest);

        // Then
        verify(orderRepository).findAllByCustomerId(3344);
        verify(orderDtoConverter).convert(order);
        assertThat(orderDtoList).containsExactly(orderDto);
    }
}