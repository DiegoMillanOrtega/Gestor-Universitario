package com.example.sigu.service.exception;

public class TareaNotFoundException extends RuntimeException {
    public TareaNotFoundException(String message) {
        super(message);
    }
}
