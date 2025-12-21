package com.example.sigu.service.exception;

import lombok.Getter;

@Getter
public class NotaCalcularRequerimientoException extends RuntimeException {

    private final String campoFaltante;

    public NotaCalcularRequerimientoException(String campoFaltante)
    {
        super(String.format("No se puede calcular la proyección: Falta el valor de [%s]. " +
                "Se requiere al menos la primera calificación parcial para realizar una estimación válida.", campoFaltante));
        this.campoFaltante = campoFaltante;
    }
}
