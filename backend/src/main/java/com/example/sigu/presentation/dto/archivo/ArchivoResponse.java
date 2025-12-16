package com.example.sigu.presentation.dto.archivo;

import com.example.sigu.persistence.enums.TipoArchivo;
import com.example.sigu.presentation.dto.materia.MateriaResponse;

public record ArchivoResponse(
        Long id,
        String nombre,
        TipoArchivo tipo,
        String url,
        String descripcion,
        MateriaResponse materia
) {
}
