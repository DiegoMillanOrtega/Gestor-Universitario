package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import com.example.sigu.presentation.dto.semestre.SemestreResponse;
import com.example.sigu.service.exception.UsuarioNotFoundException;
import com.example.sigu.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(
        componentModel = "spring",
        uses = { UsuarioMapper.class }
)
public interface SemestreMapper {

    @Mapping(target = "usuario", ignore = true)
    Semestre toEntity(SemestreRequest request);

    @Mapping(target = "progreso", expression = "java(entity.getProgreso())")
    SemestreResponse toResponse(Semestre entity);
}
