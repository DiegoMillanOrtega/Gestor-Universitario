package com.example.sigu.presentation.dto.nota;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NotaRequest(
        @DecimalMin(value = "0.0", inclusive = true, message = "La calificación mínima es 0.0.")
        @DecimalMax(value = "5.0", inclusive = true, message = "La calificación máxima es 5.0.")
        BigDecimal p1,

        @DecimalMin(value = "0.0", inclusive = true, message = "La calificación mínima es 0.0.")
        @DecimalMax(value = "5.0", inclusive = true, message = "La calificación máxima es 5.0.")
        BigDecimal p2,

        @DecimalMin(value = "0.0", inclusive = true, message = "La calificación mínima es 0.0.")
        @DecimalMax(value = "5.0", inclusive = true, message = "La calificación máxima es 5.0.")
        BigDecimal p3,

        @DecimalMin(value = "0.0", inclusive = true, message = "La calificación mínima es 0.0.")
        @DecimalMax(value = "5.0", inclusive = true, message = "La calificación máxima es 5.0.")
        BigDecimal ex,

        @NotNull(message = "La materia es obligatoria")
        Long materiaId
) {
}
