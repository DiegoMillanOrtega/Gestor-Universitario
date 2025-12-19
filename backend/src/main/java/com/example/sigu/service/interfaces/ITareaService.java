package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.presentation.dto.tarea.TareaPatchRequest;

import java.util.List;
import java.util.Optional;

public interface ITareaService {
    Tarea save(TareaRequest request);
    Tarea patch(Long tareaId, TareaPatchRequest request);
    Optional<Tarea> findById(Long id);
    List<Tarea> findAll();
    void deleteById(Long id);
}
