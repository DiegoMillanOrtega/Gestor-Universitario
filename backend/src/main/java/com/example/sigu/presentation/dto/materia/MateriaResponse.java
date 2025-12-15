package com.example.sigu.presentation.dto.materia;


import com.example.sigu.presentation.dto.SemestreResponse;

public record MateriaResponse(
        Long id,
        String nombre,
        Integer numCreditos,
        String profesor,
        String horario,
        SemestreResponse semestre
) {
}
