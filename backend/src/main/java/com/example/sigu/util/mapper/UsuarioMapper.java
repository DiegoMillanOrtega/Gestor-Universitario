package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.presentation.dto.UsuarioResponse;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(
        componentModel = "spring"
)
public interface UsuarioMapper {

    UsuarioResponse toResponse(Usuario usuario);

}
