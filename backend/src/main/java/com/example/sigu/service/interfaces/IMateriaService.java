package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;

import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<MateriaResponse> findAll();
    Optional<MateriaResponse> findById(Long id);
    Materia save(MateriaRequest materia);
    void delete(Long id);

}
