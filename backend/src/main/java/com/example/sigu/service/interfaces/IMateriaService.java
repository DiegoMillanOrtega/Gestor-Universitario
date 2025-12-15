package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.materia.MateriaRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<Materia> findAll();
    Optional<Materia> findById(Long id);
    Materia save(MateriaRequest materia);
    void delete(Long id) throws AccessDeniedException;

}
