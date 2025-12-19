package com.example.sigu.presentation.dto.archivo;

import com.example.sigu.persistence.entity.Materia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ArchivoGoogleDriveRequest(
        String nombre,
        String descripcion,
        Materia materia
) {
}
