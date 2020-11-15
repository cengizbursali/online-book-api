package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.model.request.FilterOrderDetailRequest;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import com.getir.onlinebookapi.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetailDto> getAll(FilterOrderDetailRequest filterOrderDetailRequest) {
        log.info("filterOrderDetailRequest: {}", filterOrderDetailRequest);
        return orderDetailService.getAll(filterOrderDetailRequest);
    }
}
