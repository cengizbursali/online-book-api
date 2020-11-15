package com.getir.onlinebookapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.CreateBookRequest;
import com.getir.onlinebookapi.service.BookService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@RunWith(SpringRunner.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

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
    public void it_should_create_book() throws Exception {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("Sefiller")
                .author("Victor Hugo")
                .price(BigDecimal.TEN)
                .stockQuantity(100)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/books")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createBookRequest)));

        // Then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<CreateBookRequest> createBookRequestArgumentCaptor = ArgumentCaptor.forClass(CreateBookRequest.class);
        verify(bookService).create(createBookRequestArgumentCaptor.capture());
        assertThat(createBookRequestArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(createBookRequest);
    }

    @Test
    public void it_should_throw_exception_when_name_is_blank() throws Exception {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("")
                .author("Victor Hugo")
                .price(BigDecimal.TEN)
                .stockQuantity(100)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/books")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createBookRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(bookService);
    }

    @Test
    public void it_should_throw_exception_when_author_is_blank() throws Exception {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("Sefiller")
                .author("")
                .price(BigDecimal.TEN)
                .stockQuantity(100)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/books")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createBookRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(bookService);
    }

    @Test
    public void it_should_throw_exception_when_price_is_null() throws Exception {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("Sefiller")
                .author("Victor Hugo")
                .price(null)
                .stockQuantity(100)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/books")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createBookRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(bookService);
    }

    @Test
    public void it_should_throw_exception_when_stockQuantity_less_than_1() throws Exception {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("Sefiller")
                .author("Victor Hugo")
                .price(BigDecimal.TEN)
                .stockQuantity(0)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/books")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createBookRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(bookService);
    }
}