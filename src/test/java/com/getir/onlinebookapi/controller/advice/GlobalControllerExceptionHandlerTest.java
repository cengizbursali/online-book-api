package com.getir.onlinebookapi.controller.advice;

import com.getir.onlinebookapi.MessageSourceTestConfiguration;
import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.exception.TokenGenerateException;
import com.getir.onlinebookapi.model.error.ErrorResponse;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TestController.class)
@ContextConfiguration(classes = MessageSourceTestConfiguration.class)
public class GlobalControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void it_should_resolve_exception() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/tests/generic-exception"));

        //Then
        resultActions
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.exception", is("java.lang.Exception")))
                .andExpect(jsonPath("$.errors[0]", is("Generic exception occurred.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_resolve_onlineBookApiException() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/tests/online-book-api-exception"));

        //Then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is("OnlineBookApiException")))
                .andExpect(jsonPath("$.errors[0]", is("message.key")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_resolve_methodArgumentNotValidException() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/tests/method-argument-not-valid-exception"));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors[0]", is("message.key")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_handle_bind_exception() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get("/tests/bind-exception"));

        // Then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("BindException")))
                .andExpect(jsonPath("$.errors[0]", is("message.key")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_handle_token_generate_exception() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get("/tests/token-generate-exception"));

        // Then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("TokenGenerateException")))
                .andExpect(jsonPath("$.errors[0]", is("JWT Token could not generated, token generate exception message")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_handle_authentication_failed_exception() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get("/tests/authentication-failed-exception"));

        // Then
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.exception", is("AuthenticationFailedException")))
                .andExpect(jsonPath("$.errors[0]", is("Authentication header not valid")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}

@RestController
@RequestMapping("/tests")
class TestController {

    @MockBean
    private AuthenticationService authenticationService;

    @GetMapping("/generic-exception")
    public void throwException() throws Exception {
        throw new Exception("Generic exception occurred.");
    }

    @GetMapping("/online-book-api-exception")
    public void throwOnlineBookApiException() {
        throw new OnlineBookApiException("message.key", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/method-argument-not-valid-exception")
    public void throwMethodArgumentNotValidException() throws MethodArgumentNotValidException {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("request", "fieldName", "message.key");
        given(bindingResult.getFieldErrors()).willReturn(Collections.singletonList(fieldError));
        throw new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult) {
            @Override
            public String getMessage() {
                return "method-argument-not-valid-exception";
            }
        };
    }

    @GetMapping("/bind-exception")
    public void throwBindException() throws BindException {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("request", "fieldName", "message.key");
        given(bindingResult.getFieldErrors()).willReturn(Collections.singletonList(fieldError));
        throw new BindException(bindingResult);
    }

    @GetMapping("/token-generate-exception")
    public void throwTokenGenerateException() throws TokenGenerateException {
        throw new TokenGenerateException("token generate exception message");
    }

    @GetMapping("/authentication-failed-exception")
    public void throwAuthenticationFailedException() throws AuthenticationFailedException {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Authentication header not valid")
                .exception("AuthenticationFailedException")
                .timestamp(System.currentTimeMillis())
                .build();
        throw new AuthenticationFailedException(UNAUTHORIZED, errorResponse);
    }
}