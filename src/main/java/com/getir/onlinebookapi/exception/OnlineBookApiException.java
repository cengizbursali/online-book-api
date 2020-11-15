package com.getir.onlinebookapi.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class OnlineBookApiException extends RuntimeException {

    private final String key;
    private final HttpStatus httpStatus;
    private final String[] args;

    public OnlineBookApiException(String key, HttpStatus httpStatus, String... args) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public OnlineBookApiException(String key, HttpStatus httpStatus) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.args = new String[0];
    }
}
