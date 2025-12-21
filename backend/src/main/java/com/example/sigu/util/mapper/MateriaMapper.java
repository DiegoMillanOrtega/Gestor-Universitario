package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Materia;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.interfaces.ISemestreService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper(
        componentModel = "spring",
        uses = { SemestreMapper.class }
)
public interface MateriaMapper {
    @Mapping(target = "semestre", ignore = true)
    Materia toEntity(MateriaRequest request);

    MateriaResponse toResponse(Materia materia);
}
