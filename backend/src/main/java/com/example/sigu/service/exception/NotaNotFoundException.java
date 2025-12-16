package com.example.sigu.service.exception;

public class NotaNotFoundException extends RuntimeException {
    public NotaNotFoundException(String message) {
        super(message);
    }
}
