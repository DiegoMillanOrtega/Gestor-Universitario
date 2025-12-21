package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.persistence.repository.ISemestreRepository;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import com.example.sigu.service.exception.SemesterOverlapException;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.exception.UsuarioNotFoundException;
import com.example.sigu.service.interfaces.ISemestreService;
import com.example.sigu.service.interfaces.IUsuarioService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.SemestreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemestreServiceImpl implements ISemestreService {


    private final ISemestreRepository semestreRepository;
    private final IUsuarioService usuarioService;
    private final SemestreMapper semestreMapper;
    private final SecurityUtils securityUtils;


    @Override
    public List<Semestre> findAll() {
        return semestreRepository.findAllByUsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public Semestre findById(Long id) {
        return semestreRepository.findByIdAndUsuarioId(id,securityUtils.getCurrentUserId())
                .orElseThrow(() -> new SemestreNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        Semestre semestreToDelete = semestreRepository.findById(id)
                .orElseThrow(() -> new SemestreNotFoundException(id));

        if (!semestreToDelete.getUsuario().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("Acceso denegado. No tienes permiso para eliminar este semestre.");
        }
        semestreRepository.delete(semestreToDelete);
    }

    @Override
    public Semestre save(SemestreRequest request) {
        if (semestreRepository.existsOverlap(request.fechaInicio(), request.fechaFin(), request.id(), request.usuarioId())) {
            throw new SemesterOverlapException(request.fechaInicio(), request.fechaFin());
        }

        Semestre semestre = semestreMapper.toEntity(request);
        semestre.setUsuario(usuarioService.findById(request.usuarioId()));

        return semestreRepository.save(semestre);
    }

    @Override
    public long obtenerSemanasRestantes(Long semestreId) {
        Semestre semestre = findById(semestreId);
        LocalDate hoy = LocalDate.now();

        if (hoy.isAfter(semestre.getFechaFin())) return 0;
        return ChronoUnit.WEEKS.between(hoy, semestre.getFechaFin());
    }
}
