package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.presentation.dto.UsuarioResponse;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {

    public UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
}
