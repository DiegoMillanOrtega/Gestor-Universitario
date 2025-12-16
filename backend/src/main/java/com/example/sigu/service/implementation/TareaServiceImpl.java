package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.persistence.repository.ITareaRepository;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.service.exception.TareaNotFoundException;
import com.example.sigu.service.interfaces.ITareaService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.TareaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements ITareaService {

    private final ITareaRepository repository;
    private final TareaMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public Tarea save(TareaRequest request) {
        return repository.save(mapper.toTarea(request));
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public List<Tarea> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public void deleteById(Long id) {
        Tarea tareaToDelete = findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea con ID: "+ id+ " no encontrada"));

        if (!tareaToDelete.getMateria().getSemestre().getUsuario().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("\"Acceso Denegado: No tienes permiso para eliminar la tarea con ID: \" + id");
        }

        repository.delete(tareaToDelete);
    }
}
