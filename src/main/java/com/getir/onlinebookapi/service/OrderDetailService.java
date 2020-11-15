package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.OrderDetailDtoConverter;
import com.getir.onlinebookapi.entity.OrderDetail;
import com.getir.onlinebookapi.model.request.FilterOrderDetailRequest;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import com.getir.onlinebookapi.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailDtoConverter orderDetailDtoConverter;

    public List<OrderDetailDto> getAll(FilterOrderDetailRequest filterOrderDetailRequest) {
        final List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(filterOrderDetailRequest.getOrderId());
        return orderDetails.stream().map(orderDetailDtoConverter::convert).collect(Collectors.toList());
    }
}
