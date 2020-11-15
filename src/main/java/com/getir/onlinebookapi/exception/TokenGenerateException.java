package com.getir.onlinebookapi.exception;

public class TokenGenerateException extends RuntimeException {

    public TokenGenerateException(String message) {
        super("JWT Token could not generated, " + message);
    }
}
