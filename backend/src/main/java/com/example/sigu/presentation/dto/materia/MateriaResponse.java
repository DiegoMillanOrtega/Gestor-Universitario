package com.example.sigu.presentation.dto.materia;


import com.example.sigu.persistence.enums.EstadoMateria;
import com.example.sigu.presentation.dto.semestre.SemestreResponse;

import java.math.BigDecimal;

public record MateriaResponse(
        Long id,
        String codigo,
        String nombre,
        Integer numCreditos,
        String profesor,
        String horario,
        EstadoMateria estado,
        SemestreResponse semestre,
        BigDecimal p1,
        BigDecimal p2,
        BigDecimal p3,
        BigDecimal ex,
        BigDecimal promedio
) {
}
