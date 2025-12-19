package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.presentation.dto.tarea.TareaResponse;
import com.example.sigu.presentation.dto.tarea.TareaPatchRequest;
import com.example.sigu.service.exception.ArchivoNotFoundException;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IArchivoService;
import com.example.sigu.service.interfaces.IMateriaService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
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

    public void toTarea(
            TareaPatchRequest request,
            @MappingTarget Tarea tarea,
            Materia materia,
            Archivo archivo
    ) {
        if (request.titulo() != null) tarea.setTitulo(request.titulo());
        if (request.descripcion() != null) tarea.setDescripcion(request.descripcion());
        if (request.prioridad() != null) tarea.setPrioridad(request.prioridad());
        if (request.estado() != null) tarea.setEstado(request.estado());
        if (request.fechaEntrega() != null) tarea.setFechaEntrega(request.fechaEntrega());

        if (materia != null) tarea.setMateria(materia);
        if (archivo != null) tarea.setArchivo(archivo);
    }

    public TareaResponse toTareaResponse(Tarea tarea) {
        return TareaResponse.builder()
                .id(tarea.getId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .taskId(tarea.getTaskId())
                .taskListId(tarea.getTaskListId())
                .fechaEntrega(tarea.getFechaEntrega())
                .prioridad(tarea.getPrioridad())
                .materia(materiaMapper.toMateriaResponse(tarea.getMateria()))
                .archivo(archivoMapper.toArchivoResponse(tarea.getArchivo()))
                .estado(tarea.getEstado())
                .build();
    }
}
