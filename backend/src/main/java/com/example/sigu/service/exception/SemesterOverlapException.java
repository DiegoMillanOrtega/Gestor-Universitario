package com.example.sigu.service.exception;

import java.time.LocalDate;

public class SemesterOverlapException extends RuntimeException {
    public SemesterOverlapException(LocalDate fechaInicio, LocalDate fechaFin) {
        super(String.format(
                "El rango de fechas (%s  a  %s) se solapa con un semestre existente. No se permiten rangos duplicados."
                , fechaInicio, fechaFin
        ));
    }
}
