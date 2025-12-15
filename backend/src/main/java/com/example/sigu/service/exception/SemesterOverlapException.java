package com.example.sigu.service.exception;

public class SemesterOverlapException extends RuntimeException {
    public SemesterOverlapException(String message) {
        super(message);
    }
}
