package com.getir.onlinebookapi.exception;

import com.getir.onlinebookapi.model.error.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationFailedException extends RuntimeException {

    private static final long serialVersionUID = 5714400368771697779L;

    private final HttpStatus httpStatus;
    private final ErrorResponse errorResponse;

    public AuthenticationFailedException(HttpStatus httpStatus, ErrorResponse errorResponse) {
        this.httpStatus = httpStatus;
        this.errorResponse = errorResponse;
    }
}