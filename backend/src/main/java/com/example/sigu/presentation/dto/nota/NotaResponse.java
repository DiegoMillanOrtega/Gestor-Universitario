package com.example.sigu.presentation.dto.nota;

import com.example.sigu.presentation.dto.materia.MateriaResponse;

import java.math.BigDecimal;

public record NotaResponse(
        Long id,
        BigDecimal p1,
        BigDecimal p2,
        BigDecimal p3,
        BigDecimal ex,
        MateriaResponse materia
) {
}
