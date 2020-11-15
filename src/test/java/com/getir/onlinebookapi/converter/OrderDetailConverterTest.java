package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.entity.enums.OrderStatus;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.service.BookService;
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
public class OrderDetailConverterTest {

    @InjectMocks
    private OrderDetailConverter orderDetailConverter;

    @Mock
    private BookService bookService;

    @Test
    public void it_should_build_order_detail_given_bookMap() {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        bookMap.put(1001, 1);
        bookMap.put(1002, 2);
        BookDto bookDto1 = BookDto.builder().price(BigDecimal.TEN).build();
        BookDto bookDto2 = BookDto.builder().price(BigDecimal.valueOf(25)).build();
        when(bookService.getById(1001)).thenReturn(bookDto1);
        when(bookService.getById(1002)).thenReturn(bookDto2);

        // When
        List<OrderDetail> orderDetails = orderDetailConverter.convertAsList(bookMap);

        // Then
        verify(bookService).getById(1001);
        verify(bookService).getById(1002);
        assertThat(orderDetails.size()).isEqualTo(2);
        assertThat(orderDetails.get(0).getBookId()).isEqualTo(1001);
        assertThat(orderDetails.get(0).getQuantity()).isEqualTo(1);
        assertThat(orderDetails.get(0).getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(orderDetails.get(0).getOrderStatus()).isEqualTo(OrderStatus.AWAITING_APPROVAL);

        assertThat(orderDetails.get(1).getBookId()).isEqualTo(1002);
        assertThat(orderDetails.get(1).getQuantity()).isEqualTo(2);
        assertThat(orderDetails.get(1).getPrice()).isEqualTo(BigDecimal.valueOf(50));
        assertThat(orderDetails.get(1).getOrderStatus()).isEqualTo(OrderStatus.AWAITING_APPROVAL);
    }
}