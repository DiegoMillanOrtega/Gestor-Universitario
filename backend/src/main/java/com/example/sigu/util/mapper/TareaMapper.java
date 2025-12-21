package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.presentation.dto.tarea.TareaResponse;
import com.example.sigu.presentation.dto.tarea.TareaPatchRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { MateriaMapper.class, ArchivoMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TareaMapper {

    @Mapping(target = "materia", ignore = true)
    @Mapping(target = "archivo", ignore = true)
    Tarea toEntity(TareaRequest request);

    @Mapping(target = "materia", ignore = true)
    @Mapping(target = "archivo", ignore = true)
    void updateEntityFromPatch(TareaPatchRequest request, @MappingTarget Tarea entity);

    TareaResponse toResponse(Tarea tarea);

}
