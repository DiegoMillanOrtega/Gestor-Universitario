package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Materia;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.interfaces.ISemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MateriaMapper {

    private final ISemestreService semestreService;
    private final SemestreMapper semestreMapper;

    public Materia toMateria(MateriaRequest materiaRequest) {
        Semestre semestre = semestreService.findById(materiaRequest.semestreId())
                .orElseThrow(()-> new SemestreNotFoundException("No se encontró el semestre con ID: " + materiaRequest.semestreId()));

        return Materia.builder()
                .id(materiaRequest.id())
                .nombre(materiaRequest.nombre())
                .numCreditos(materiaRequest.numCreditos())
                .profesor(materiaRequest.profesor())
                .horario(materiaRequest.horario())
                .semestre(semestre)
                .build();
    }

    public MateriaResponse toMateriaResponse(Materia materia) {
        return new MateriaResponse(
                materia.getId(),
                materia.getNombre(),
                materia.getNumCreditos(),
                materia.getProfesor(),
                materia.getHorario(),
                semestreMapper.toSemestreResponse(materia.getSemestre())
        );
    }
}
