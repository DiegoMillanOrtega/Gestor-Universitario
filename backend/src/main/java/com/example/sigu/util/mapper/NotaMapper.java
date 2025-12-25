package com.example.sigu.util.mapper;


import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaResponse;
import com.example.sigu.presentation.dto.nota.NotaUpdateRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { MateriaMapper.class },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NotaMapper {

    @Mapping(target = "materia",  ignore = true)
    Nota toEntity(NotaRequest request);

    @Mapping(target = "materia", ignore = true)
    void toEntityUpdateDto(NotaUpdateRequest request, @MappingTarget Nota entity);

    NotaResponse toResponse(Nota nota);
}
