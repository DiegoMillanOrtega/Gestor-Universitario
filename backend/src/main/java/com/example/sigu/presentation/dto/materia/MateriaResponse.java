package com.example.sigu.presentation.dto.materia;


import com.example.sigu.presentation.dto.semestre.SemestreResponse;

import java.math.BigDecimal;

public record MateriaResponse(
        Long id,
        String nombre,
        Integer numCreditos,
        String profesor,
        String horario,
        SemestreResponse semestre,
        BigDecimal p1,
        BigDecimal p2,
        BigDecimal p3,
        BigDecimal ex,
        BigDecimal promedio
) {
}
