package com.example.sigu.presentation.dto.semestre;

import com.example.sigu.presentation.dto.UsuarioResponse;

import java.time.LocalDate;

public record SemestreResponse(
        Long id,
        String nombre,
        Integer anio,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Boolean semestreActual,
        UsuarioResponse usuario
) {
}
