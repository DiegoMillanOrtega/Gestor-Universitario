package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.archivo.ArchivoGoogleDriveRequest;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.presentation.dto.archivo.ArchivoResponse;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import com.google.api.services.drive.model.File;
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
                .nombre(archivoRequest.nombre())
                .descripcion(archivoRequest.descripcion())
                .materia(materia)
                .build();
    }


    public ArchivoResponse toArchivoResponse(Archivo archivo) {
        return new ArchivoResponse(
                archivo.getId(),
                archivo.getNombre(),
                archivo.getMimeType(),
                archivo.getTamano(),
                archivo.getGoogleDriveFileId(),
                archivo.getMateriaFolderId(),
                archivo.getSemestreFolderId(),
                archivo.getGoogleDriveWebViewLink(),
                archivo.getDescripcion(),
                archivo.getFechaModificacion(),
                materiaMapper.toMateriaResponse(archivo.getMateria()),
                null
        );
    }

    public ArchivoGoogleDriveRequest toArchivoGoogleDriveRequest(ArchivoRequest request) {
        Materia materia = materiaService.findById(request.materiaId())
                .orElseThrow(() -> new MateriaNotFoundException("La materia con ID: " + request.materiaId() +" no existe"));

        return ArchivoGoogleDriveRequest.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .materia(materia)
                .build();
    }



}
