package com.cardanoj.rust.annotation.processor.exception;

public class NotSupportedException extends Throwable {
    public NotSupportedException(String msg) {
        super(msg);
    }
}
