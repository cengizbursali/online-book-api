package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.entity.enums.OrderStatus;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.FilterOrderDetailRequest;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.model.response.OrderDetailDto;
import com.getir.onlinebookapi.service.OrderDetailService;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDetailController.class)
@RunWith(SpringRunner.class)
public class OrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

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
    public void it_should_get_order_detail_by_order_id() throws Exception {
        // Given
        OrderDetailDto orderDetailDto1 = OrderDetailDto.builder()
                .id(54)
                .bookDto(BookDto.builder()
                                 .name("Clean Code")
                                 .price(BigDecimal.TEN)
                                 .author("Robert Cecil Martin")
                                 .build())
                .quantity(2)
                .orderStatus(OrderStatus.AWAITING_APPROVAL)
                .orderId(100)
                .price(BigDecimal.valueOf(20))
                .build();
        OrderDetailDto orderDetailDto2 = OrderDetailDto.builder()
                .id(55)
                .bookDto(BookDto.builder()
                                 .name("Effective Java")
                                 .price(BigDecimal.TEN)
                                 .author("Joshua Bloch")
                                 .build())
                .quantity(1)
                .orderStatus(OrderStatus.APPROVED)
                .orderId(100)
                .price(BigDecimal.TEN)
                .build();
        when(orderDetailService.getAll(any(FilterOrderDetailRequest.class))).thenReturn(List.of(orderDetailDto1, orderDetailDto2));

        // When
        ResultActions resultActions = mockMvc.perform(get("/order-details?orderId=100"));

        // Then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<FilterOrderDetailRequest> filterOrderDetailRequestArgumentCaptor = ArgumentCaptor.forClass(FilterOrderDetailRequest.class);
        verify(orderDetailService).getAll(filterOrderDetailRequestArgumentCaptor.capture());
        assertThat(filterOrderDetailRequestArgumentCaptor.getValue().getOrderId()).isEqualTo(100);

        resultActions.andExpect(jsonPath("$[0].id").value(54));
        resultActions.andExpect(jsonPath("$[0].quantity").value(2));
        resultActions.andExpect(jsonPath("$[0].orderStatus").value("AWAITING_APPROVAL"));
        resultActions.andExpect(jsonPath("$[0].orderId").value(100));
        resultActions.andExpect(jsonPath("$[0].price").value(BigDecimal.valueOf(20)));
        resultActions.andExpect(jsonPath("$[0].bookDto.name").value("Clean Code"));
        resultActions.andExpect(jsonPath("$[0].bookDto.price").value(BigDecimal.TEN));
        resultActions.andExpect(jsonPath("$[0].bookDto.author").value("Robert Cecil Martin"));

        resultActions.andExpect(jsonPath("$[1].id").value(55));
        resultActions.andExpect(jsonPath("$[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$[1].orderStatus").value("APPROVED"));
        resultActions.andExpect(jsonPath("$[1].orderId").value(100));
        resultActions.andExpect(jsonPath("$[1].price").value(BigDecimal.valueOf(10)));
        resultActions.andExpect(jsonPath("$[1].bookDto.name").value("Effective Java"));
        resultActions.andExpect(jsonPath("$[1].bookDto.price").value(BigDecimal.TEN));
        resultActions.andExpect(jsonPath("$[1].bookDto.author").value("Joshua Bloch"));
    }
}