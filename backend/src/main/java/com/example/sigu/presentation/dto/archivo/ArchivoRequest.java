package com.example.sigu.presentation.dto.archivo;

import com.example.sigu.persistence.enums.TipoArchivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ArchivoRequest(

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres.")
    String descripcion,

    @NotNull(message = "El archivo debe estar asociado a una materia.")
    @Positive(message = "El ID de la materia debe ser un valor positivo.")
    Long materiaId
) {
}
