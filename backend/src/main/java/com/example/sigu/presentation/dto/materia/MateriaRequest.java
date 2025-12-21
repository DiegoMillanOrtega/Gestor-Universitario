package com.example.sigu.presentation.dto.materia;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MateriaRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El número de créditos es obligatorio")
        @Min(value = 0, message = "Los créditos deben ser al menos 0")
        Integer numCreditos,

        String profesor,
        String horario,

        @NotNull(message = "La materia debe estar asociada a un semestre")
        Long semestreId
) {
}
