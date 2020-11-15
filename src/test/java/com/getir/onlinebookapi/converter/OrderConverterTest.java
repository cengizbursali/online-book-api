package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Order;
import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {

    @InjectMocks
    private OrderConverter orderConverter;

    @Mock
    private OrderDetailConverter orderDetailConverter;

    @Test
    public void it_should_build_order() {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .customerId(1)
                .bookMap(bookMap)
                .build();

        OrderDetail orderDetail1 = OrderDetail.builder().price(BigDecimal.TEN).build();
        OrderDetail orderDetail2 = OrderDetail.builder().price(BigDecimal.valueOf(25)).build();
        List<OrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);
        when(orderDetailConverter.convertAsList(bookMap)).thenReturn(orderDetails);

        // When
        Order order = orderConverter.convert(createOrderRequest);

        // Then
        verify(orderDetailConverter).convertAsList(bookMap);
        assertThat(order.getCustomerId()).isEqualTo(1);
        assertThat(order.getOrderDetails()).isEqualTo(orderDetails);
        assertThat(order.getPrice()).isEqualTo(BigDecimal.valueOf(35));
    }
}