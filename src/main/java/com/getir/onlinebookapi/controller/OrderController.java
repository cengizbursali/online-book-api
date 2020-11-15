package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import com.getir.onlinebookapi.model.request.FilterOrderRequest;
import com.getir.onlinebookapi.model.response.OrderDto;
import com.getir.onlinebookapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        log.info("createOrderRequest: {}", createOrderRequest);
        orderService.create(createOrderRequest);
    }

    @GetMapping
    public List<OrderDto> getAll(FilterOrderRequest filterOrderRequest) {
        log.info("filterOrderRequest: {}", filterOrderRequest);
        return orderService.getAll(filterOrderRequest);
    }
}
