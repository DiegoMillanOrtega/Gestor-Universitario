package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;

import java.util.List;
import java.util.Optional;

public interface INotaService {
    List<Nota> findAll();
    Optional<Nota> findById(Long id);
    Nota save(NotaRequest request);
    void delete(Long id);
}
