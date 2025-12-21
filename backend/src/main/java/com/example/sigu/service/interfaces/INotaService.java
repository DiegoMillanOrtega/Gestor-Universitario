package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaUpdateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface INotaService {
    List<Nota> findAll();
    Nota findById(Long id);
    Nota save(NotaRequest request);
    Nota update(Long notaId, NotaUpdateRequest request);
    void delete(Long id);
    Map<String, BigDecimal> calcularRequerimiento(Long notaId);
}
