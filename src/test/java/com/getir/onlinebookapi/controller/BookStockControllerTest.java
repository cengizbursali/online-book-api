package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.model.response.BookStockDto;
import com.getir.onlinebookapi.service.BookStockService;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookStockController.class)
@RunWith(SpringRunner.class)
public class BookStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStockService bookStockService;

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
    public void it_should_get_all_book_stock() throws Exception {
        // Given
        BookStockDto bookStockDto = BookStockDto.builder()
                .stockQuantity(23)
                .bookDto(BookDto.builder()
                                 .id(1)
                                 .name("Effective Java")
                                 .author("Joshua Bloch")
                                 .price(BigDecimal.TEN)
                                 .build())
                .build();
        when(bookStockService.getAll()).thenReturn(List.of(bookStockDto));

        // When
        ResultActions resultActions = mockMvc.perform(get("/book-stocks"));

        // Then
        verify(bookStockService).getAll();
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$[0].stockQuantity").value(23));
        resultActions.andExpect(jsonPath("$[0].bookDto.id").value(1));
        resultActions.andExpect(jsonPath("$[0].bookDto.name").value("Effective Java"));
        resultActions.andExpect(jsonPath("$[0].bookDto.author").value("Joshua Bloch"));
        resultActions.andExpect(jsonPath("$[0].bookDto.price").value(BigDecimal.TEN));
    }
}