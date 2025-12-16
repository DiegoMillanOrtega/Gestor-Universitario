package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.entity.Parcial;
import com.example.sigu.presentation.dto.parcial.ParcialRequest;
import com.example.sigu.presentation.dto.parcial.ParcialResponse;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcialMapper {

    private final IMateriaService materiaService;
    private final MateriaMapper materiaMapper;

    public ParcialResponse toParcialResponse(Parcial parcial) {
        return new ParcialResponse(
                parcial.getId(),
                parcial.getTipo(),
                parcial.getFecha(),
                parcial.getHora(),
                parcial.getLugar(),
                parcial.getTemaEvaluar(),
                parcial.getNotaAdicional(),
                materiaMapper.toMateriaResponse(parcial.getMateria())
        );
    }

    public Parcial toParcial(ParcialRequest request) {
        Materia materia = materiaService.findById(request.materiaId())
                .orElseThrow(() -> new MateriaNotFoundException("Materia con ID: " + request.materiaId() + " no encontrada"));


        return Parcial.builder()
                .id(request.id())
                .tipo(request.tipo())
                .fecha(request.fecha())
                .hora(request.hora())
                .lugar(request.lugar())
                .temaEvaluar(request.temaEvaluar())
                .notaAdicional(request.notaAdicional())
                .materia(materia)
                .build();
    }
}
