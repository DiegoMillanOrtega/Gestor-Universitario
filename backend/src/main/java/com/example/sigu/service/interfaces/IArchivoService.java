package com.example.sigu.service.interfaces;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface IArchivoService {
    Archivo create(ArchivoRequest archivoRequest);
    Optional<Archivo> findById(Long id);
    List<Archivo> findAll();
    void  delete(Long id);
}
