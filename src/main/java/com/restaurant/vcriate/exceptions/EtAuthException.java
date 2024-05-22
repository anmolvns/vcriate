package com.restaurant.vcriate.exceptions;

public class EtAuthException extends RuntimeException {
    public EtAuthException(String message) {
        super(message);
    }
    public EtAuthException(String message, Throwable cause){
        super(message, cause);
    }
}