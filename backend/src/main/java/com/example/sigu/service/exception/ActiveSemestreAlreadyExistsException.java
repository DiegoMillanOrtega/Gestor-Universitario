package com.example.sigu.service.exception;

public class ActiveSemestreAlreadyExistsException extends RuntimeException {
    public ActiveSemestreAlreadyExistsException() {
        super("Ya existe un semestre con estado ACTIVO. No se permiten múltiples semestres activos simultáneamente.");
    }
}
