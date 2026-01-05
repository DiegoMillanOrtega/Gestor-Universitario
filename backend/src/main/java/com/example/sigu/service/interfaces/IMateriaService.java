package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.materia.MateriaRequest;

import java.util.List;

public interface IMateriaService {
    List<Materia> findAll();
    List<Materia> findAllBySemestreActivo();
    List<Materia> buscarTodoPorSemestreId(Long semestreId);
    Materia findById(Long id);
    Materia save(MateriaRequest materia);
    void delete(Long id);

}
