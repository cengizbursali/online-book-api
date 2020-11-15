package com.getir.onlinebookapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.CreateCustomerRequest;
import com.getir.onlinebookapi.service.CustomerService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

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
    public void it_should_create_customer() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("cengizbursali@gmail.com")
                .name("cengiz")
                .surname("bursali")
                .age(28)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<CreateCustomerRequest> createCustomerRequestArgumentCaptor = ArgumentCaptor.forClass(CreateCustomerRequest.class);
        verify(customerService).create(createCustomerRequestArgumentCaptor.capture());
        assertThat(createCustomerRequestArgumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(createCustomerRequest);
    }

    @Test
    public void it_should_throw_exception_when_surname_is_blank() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("cengizbursali@gmail.com")
                .name("cengiz")
                .surname("")
                .age(28)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    public void it_should_throw_exception_when_name_is_blank() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("cengizbursali@gmail.com")
                .name("")
                .surname("bursali")
                .age(28)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    public void it_should_throw_exception_when_email_is_null() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email(null)
                .name("cengiz")
                .surname("bursali")
                .age(28)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    public void it_should_throw_exception_when_email_is_not_valid() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("cengiz@")
                .name("cengiz")
                .surname("bursali")
                .age(28)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    public void it_should_throw_exception_when_age_less_than_18() throws Exception {
        // Given
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("cengizbursali@gmail.com")
                .name("cengiz")
                .surname("bursali")
                .age(17)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/customers")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(new ObjectMapper().writeValueAsString(createCustomerRequest)));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }
}