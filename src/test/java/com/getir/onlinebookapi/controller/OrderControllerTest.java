package com.getir.onlinebookapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.CreateOrderRequest;
import com.getir.onlinebookapi.model.request.FilterOrderRequest;
import com.getir.onlinebookapi.model.response.OrderDto;
import com.getir.onlinebookapi.service.OrderService;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@RunWith(SpringRunner.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthenticationService authenticationService;

    @Before
    public void setUp() {
        User user = new User();
        UserAuthentication authentication = new UserAuthentication(user);
        when(authenticationService.getUserAuthentication(any())).thenReturn(authentication);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void it_should_create_order() throws Exception {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        bookMap.put(1001, 1);
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .bookMap(bookMap)
                .customerId(1)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/orders")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createOrderRequest)));

        // Then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<CreateOrderRequest> createOrderRequestArgumentCaptor = ArgumentCaptor.forClass(CreateOrderRequest.class);
        verify(orderService).create(createOrderRequestArgumentCaptor.capture());
        assertThat(createOrderRequestArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(createOrderRequest);
    }

    @Test
    public void it_should_throw_exception_when_bookMap_is_empty() throws Exception {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .bookMap(bookMap)
                .customerId(1)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/orders")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createOrderRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(orderService);
    }

    @Test
    public void it_should_throw_exception_when_customer_id_is_null() throws Exception {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .bookMap(bookMap)
                .customerId(null)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/orders")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createOrderRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(orderService);
    }

    @Test
    public void it_should_get_all_order() throws Exception {
        // Given
        LocalDateTime createdDate = LocalDateTime.of(2020, 11, 11, 12, 30);
        OrderDto orderDto = OrderDto.builder()
                .id(1)
                .price(BigDecimal.TEN)
                .customerId(3344)
                .createdDate(createdDate)
                .build();
        when(orderService.getAll(any(FilterOrderRequest.class))).thenReturn(List.of(orderDto));

        // When
        ResultActions resultActions = mockMvc.perform(get("/orders?customerId=3344"));

        // Then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<FilterOrderRequest> filterOrderRequestArgumentCaptor = ArgumentCaptor.forClass(FilterOrderRequest.class);
        verify(orderService).getAll(filterOrderRequestArgumentCaptor.capture());
        assertThat(filterOrderRequestArgumentCaptor.getValue().getCustomerId()).isEqualTo(3344);

        resultActions.andExpect(jsonPath("$[0].id").value(1));
        resultActions.andExpect(jsonPath("$[0].price").value(BigDecimal.TEN));
        resultActions.andExpect(jsonPath("$[0].customerId").value(3344));
        resultActions.andExpect(jsonPath("$[0].createdDate").value("2020-11-11T12:30:00"));
    }
}