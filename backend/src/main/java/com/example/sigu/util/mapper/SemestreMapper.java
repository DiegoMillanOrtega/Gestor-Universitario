package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import com.example.sigu.presentation.dto.semestre.SemestreResponse;
import com.example.sigu.service.exception.UsuarioNotFoundException;
import com.example.sigu.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemestreMapper {

    private final UsuarioMapper usuarioMapper;
    private final IUsuarioService usuarioService;

    public SemestreResponse toSemestreResponse(Semestre semestre) {
        return new SemestreResponse(
                semestre.getId(),
                semestre.getNombre(),
                semestre.getAnio(),
                semestre.getFechaInicio(),
                semestre.getFechaFin(),
                semestre.getSemestreActual(),
                usuarioMapper.toUsuarioResponse(semestre.getUsuario())
        );
    }

    public Semestre toSemestre(SemestreRequest request) {

        Usuario usuario = usuarioService.findById(request.usuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario con ID: "+ request.usuarioId()+" no existe"));

        return Semestre.builder()
                .id(request.id())
                .nombre(request.nombre())
                .anio(request.anio())
                .fechaInicio(request.fechaInicio())
                .fechaFin(request.fechaFin())
                .semestreActual(request.semestreActual())
                .usuario(usuario)
                .build();
    }
}
