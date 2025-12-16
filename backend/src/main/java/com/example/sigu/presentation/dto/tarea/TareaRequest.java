package com.example.sigu.presentation.dto.tarea;

import com.example.sigu.persistence.enums.Estado;
import com.example.sigu.persistence.enums.Prioridad;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TareaRequest(
        Long id,

        @NotBlank(message = "El titulo es obligatorio")
        String titulo,
        String descripcion,

        @NotNull(message = "La fecha de entrega es obligatoria")
        @FutureOrPresent(message = "La fecha de entrega debe ser hoy o posterior.")
        LocalDate fechaEntrega,

        @NotNull(message = "La prioriodad es obligatoria")
        Prioridad prioridad,

        @NotNull(message = "La materia es obligatoria")
        Long materiaId,

        Long archivoId,

        @NotNull(message = "El estado es obligatorio")
        Estado estado
) {
}
