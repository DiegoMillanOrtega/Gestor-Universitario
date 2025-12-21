package com.example.sigu.service.exception;

import lombok.Getter;

@Getter
public class NotaNotFoundException extends RuntimeException {
    public NotaNotFoundException(Long notaId) {
        super(String.format("Lo sentimos, la nota con ID '%s' no se encuentra disponible o no existe en el sistema.", notaId));
    }
}
