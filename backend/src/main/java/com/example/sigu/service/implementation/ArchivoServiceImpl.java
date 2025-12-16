package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.repository.IArchivoRepository;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.service.exception.ArchivoNotFoundException;
import com.example.sigu.service.interfaces.IArchivoService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.ArchivoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements IArchivoService {

    private final IArchivoRepository repository;
    private final ArchivoMapper archivoMapper;
    private final SecurityUtils securityUtils;

    @Override
    public Archivo create(ArchivoRequest archivoRequest) {
        return repository.save(archivoMapper.toArchivo(archivoRequest));
    }

    @Override
    public Optional<Archivo> findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public List<Archivo> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public void delete(Long id){
        Archivo archivoToDelete = findById(id)
                .orElseThrow(() -> new ArchivoNotFoundException("El archivo con el ID: "+ id+" no existe"));

        if (!archivoToDelete.getMateria().getSemestre().getUsuario().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("Acceso Denegado: No tienes permiso para eliminar la materia con ID: " + id);
        }

        repository.delete(archivoToDelete);
    }
}
