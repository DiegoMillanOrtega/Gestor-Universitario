package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface ISemestreService {
    List<Semestre> findAll();
    Optional<Semestre> findById(Long id);
    void  deleteById(Long id);
    Semestre save(SemestreRequest request);
}
