package com.example.sigu.presentation.dto.materia;

import com.example.sigu.persistence.enums.EstadoMateria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MateriaRequest(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El codigo es obligatorio")
        String codigo,

        @NotNull(message = "El número de créditos es obligatorio")
        @Min(value = 0, message = "Los créditos deben ser al menos 0")
        Integer numCreditos,

        String profesor,
        String horario,

        @NotNull(message = "El estado es obligatorio")
        EstadoMateria estado,

        @NotNull(message = "La materia debe estar asociada a un semestre")
        Long semestreId
) {
}
