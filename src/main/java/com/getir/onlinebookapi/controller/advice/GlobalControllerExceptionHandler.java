package com.getir.onlinebookapi.controller.advice;

import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.exception.TokenGenerateException;
import com.getir.onlinebookapi.model.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    private static final Locale TR = new Locale("tr");
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Generic exception occurred.", exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .exception(exception.getClass().getName())
                .timestamp(System.currentTimeMillis())
                .errors(Collections.singletonList("Generic exception occurred."))
                .build();
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OnlineBookApiException.class)
    public ResponseEntity<ErrorResponse> handleOnlineBookApiException(OnlineBookApiException exception) {
        log.error("OnlineBookApiException occurred.", exception);
        final String exceptionKey = exception.getKey();
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .exception("OnlineBookApiException")
                .error(messageSource.getMessage(exceptionKey, exception.getArgs(), exceptionKey, TR))
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException occurred.", exception);
        final List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(this::getMessage)
                .collect(Collectors.toList());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .exception("MethodArgumentNotValidException")
                .errors(errorMessages)
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        log.error("BindException occurred.", exception);
        final List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(this::getMessage)
                .collect(Collectors.toList());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .exception("BindException")
                .errors(errorMessages)
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException exception) {
        log.error("AuthenticationFailedException occurred.", exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenGenerateException.class)
    public ResponseEntity<ErrorResponse> handleTokenGenerateException(TokenGenerateException exception) {
        log.error("TokenGenerateException occurred.", exception);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .exception("TokenGenerateException")
                .error(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(FieldError error) {
        final String messageKey = error.getDefaultMessage();
        return messageSource.getMessage(messageKey, error.getArguments(), messageKey, TR);
    }
}
