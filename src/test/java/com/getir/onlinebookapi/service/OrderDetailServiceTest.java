package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.OrderDetailDtoConverter;
import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.model.request.FilterOrderDetailRequest;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import com.getir.onlinebookapi.repository.OrderDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailServiceTest {

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailDtoConverter orderDetailDtoConverter;

    @Test
    public void it_should_get_order_detail_list() {
        // Given
        FilterOrderDetailRequest filterOrderDetailRequest = new FilterOrderDetailRequest(555);
        OrderDetail orderDetail = OrderDetail.builder().build();
        when(orderDetailRepository.findAllByOrderId(555)).thenReturn(List.of(orderDetail));
        OrderDetailDto orderDetailDto = OrderDetailDto.builder().build();
        when(orderDetailDtoConverter.convert(orderDetail)).thenReturn(orderDetailDto);

        // When
        List<OrderDetailDto> orderDetailDtoList = orderDetailService.getAll(filterOrderDetailRequest);

        // Then
        assertThat(orderDetailDtoList).containsExactly(orderDetailDto);
        verify(orderDetailRepository).findAllByOrderId(555);
        verify(orderDetailDtoConverter).convert(orderDetail);
    }
}