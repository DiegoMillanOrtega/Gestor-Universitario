package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.persistence.repository.ISemestreRepository;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import com.example.sigu.service.exception.SemesterOverlapException;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.interfaces.ISemestreService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.SemestreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemestreServiceImpl implements ISemestreService {


    private final ISemestreRepository semestreRepository;
    private final SemestreMapper semestreMapper;
    private final SecurityUtils securityUtils;


    @Override
    public List<Semestre> findAll() {
        return semestreRepository.findAllByUsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public Optional<Semestre> findById(Long id) {
        return semestreRepository.findByIdAndUsuarioId(id,securityUtils.getCurrentUserId());
    }

    @Override
    public void deleteById(Long id) throws AccessDeniedException {
        Semestre semestreToDelete = semestreRepository.findById(id)
                .orElseThrow(() -> new SemestreNotFoundException("El semestre con ID: "+ id + " no existe"));

        if (!semestreToDelete.getUsuario().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("Acceso denegado. No tienes permiso para eliminar este semestre.");
        }
        semestreRepository.delete(semestreToDelete);
    }

    @Override
    public Semestre save(SemestreRequest request) {
        if (semestreRepository.existsOverlap(request.fechaInicio(), request.fechaFin(), request.id(), request.usuarioId())) {
            throw new SemesterOverlapException(
                    "El rango de fechas (" + request.fechaInicio() + " a " + request.fechaFin() +
                            ") se solapa con un semestre existente. No se permiten rangos duplicados."
            );
        }
        return semestreRepository.save(semestreMapper.toSemestre(request));
    }
}
