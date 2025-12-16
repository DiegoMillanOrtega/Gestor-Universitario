package com.example.sigu.util.mapper;


import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaResponse;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotaMapper {

    private final IMateriaService materiaService;
    private final MateriaMapper materiaMapper;

    public Nota toNota(NotaRequest request) {

        Materia materia = materiaService.findById(request.materiaId())
                .orElseThrow(() -> new MateriaNotFoundException("Materia con ID: " + request.materiaId() + " no encontrada"));

        return Nota.builder()
                .id(request.id())
                .p1(request.p1())
                .p2(request.p2())
                .p3(request.p3())
                .ex(request.ex())
                .materia(materia)
                .build();
    }

    public NotaResponse toNotaResponse(Nota nota) {
        return new NotaResponse(
                nota.getId(),
                nota.getP1(),
                nota.getP2(),
                nota.getP3(),
                nota.getEx(),
                materiaMapper.toMateriaResponse(nota.getMateria())
        );
    }

}
