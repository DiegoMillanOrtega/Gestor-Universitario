package com.example.sigu.presentation.dto.tarea;

import com.example.sigu.persistence.enums.Estado;
import com.example.sigu.persistence.enums.Prioridad;
import com.example.sigu.presentation.dto.archivo.ArchivoResponse;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TareaResponse(
        Long id,
        String titulo,
        String descripcion,
        LocalDate fechaEntrega,
        Prioridad prioridad,
        MateriaResponse materia,
        ArchivoResponse archivo,
        Estado estado
) {
}
