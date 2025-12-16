package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.presentation.dto.tarea.TareaResponse;
import com.example.sigu.service.exception.ArchivoNotFoundException;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IArchivoService;
import com.example.sigu.service.interfaces.IMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TareaMapper {

    private final IMateriaService materiaService;
    private final IArchivoService archivoService;
    private final MateriaMapper materiaMapper;
    private final ArchivoMapper archivoMapper;

    public Tarea toTarea(TareaRequest request) {
        Materia materia = materiaService.findById(request.materiaId())
                .orElseThrow(() -> new MateriaNotFoundException("La materia con ID: " + request.materiaId() +" no existe"));

        Archivo archivo = archivoService.findById(request.archivoId())
                .orElseThrow(() -> new ArchivoNotFoundException("El archivo con ID: " + request.archivoId() +" no existe"));

        return Tarea.builder()
                .id(request.id())
                .titulo(request.titulo())
                .descripcion(request.descripcion())
                .fechaEntrega(request.fechaEntrega())
                .prioridad(request.prioridad())
                .materia(materia)
                .archivo(archivo)
                .estado(request.estado())
                .build();
    }

    public TareaResponse toTareaResponse(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaEntrega(),
                tarea.getPrioridad(),
                materiaMapper.toMateriaResponse(tarea.getMateria()),
                archivoMapper.toArchivoResponse(tarea.getArchivo()),
                tarea.getEstado()
        );
    }
}
