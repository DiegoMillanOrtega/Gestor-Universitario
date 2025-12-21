package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;

import java.util.List;

public interface ISemestreService {
    List<Semestre> findAll();
    Semestre findById(Long id);
    void  deleteById(Long id);
    Semestre save(SemestreRequest request);
    long obtenerSemanasRestantes(Long semestreId);
}
