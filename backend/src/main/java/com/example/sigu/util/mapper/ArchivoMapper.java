package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.presentation.dto.archivo.ArchivoResponse;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchivoMapper {

    private final IMateriaService materiaService;
    private final MateriaMapper materiaMapper;

    public Archivo toArchivo(ArchivoRequest archivoRequest) {
        Materia materia = materiaService.findById(archivoRequest.materiaId())
                .orElseThrow(() -> new MateriaNotFoundException("La materia con ID: " + archivoRequest.materiaId() +" no existe"));

        return Archivo.builder()
                .id(archivoRequest.id())
                .nombre(archivoRequest.nombre())
                .tipo(archivoRequest.tipo())
                .url(archivoRequest.url())
                .descripcion(archivoRequest.descripcion())
                .materia(materia)
                .build();
    }

    public ArchivoResponse toArchivoResponse(Archivo archivo) {
        return new ArchivoResponse(
                archivo.getId(),
                archivo.getNombre(),
                archivo.getTipo(),
                archivo.getUrl(),
                archivo.getDescripcion(),
                materiaMapper.toMateriaResponse(archivo.getMateria())
        );
    }
}
