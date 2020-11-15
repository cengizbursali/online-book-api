package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.entity.enums.OrderStatus;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailDtoConverterTest {

    @InjectMocks
    private OrderDetailDtoConverter orderDetailDtoConverter;

    @Mock
    private BookDtoConverter bookDtoConverter;

    @Test
    public void it_should_convert() {
        // Given
        Book book = Book.builder().build();
        OrderDetail orderDetail = OrderDetail.builder()
                .id(1)
                .orderId(2)
                .quantity(3)
                .orderStatus(OrderStatus.AWAITING_APPROVAL)
                .price(BigDecimal.TEN)
                .book(book)
                .build();
        BookDto bookDto = BookDto.builder().build();
        when(bookDtoConverter.convert(book)).thenReturn(bookDto);

        // When
        OrderDetailDto orderDetailDto = orderDetailDtoConverter.convert(orderDetail);

        // Then
        assertThat(orderDetailDto.getId()).isEqualTo(1);
        assertThat(orderDetailDto.getOrderId()).isEqualTo(2);
        assertThat(orderDetailDto.getQuantity()).isEqualTo(3);
        assertThat(orderDetailDto.getOrderStatus()).isEqualTo(OrderStatus.AWAITING_APPROVAL);
        assertThat(orderDetailDto.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(orderDetailDto.getBookDto()).isEqualTo(bookDto);

    }
}