package com.org.cardanoJ.blockfrost.exception;

public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Exception e) {
        super(message, e);
    }
}
