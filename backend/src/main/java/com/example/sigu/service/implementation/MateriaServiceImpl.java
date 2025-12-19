package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.repository.IMateriaRepository;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.MateriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements IMateriaService {


    private final IMateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;
    private final SecurityUtils securityUtils;

    @Override
    public List<Materia> findAll() {
        return materiaRepository.findAllBySemestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public List<Materia> buscarTodoPorSemestreId(Long semestreId) {
        return materiaRepository.findAllBySemestreIdAndSemestre_UsuarioId(semestreId, securityUtils.getCurrentUserId());
    }

    @Override
    public Optional<Materia> findById(Long id) {
        return materiaRepository.findByIdAndSemestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public Materia save(MateriaRequest materiaRequest) {
        return materiaRepository.save(materiaMapper.toMateria(materiaRequest));
    }

    @Override
    public void delete(Long id) {
        Materia materiaToDelete = materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada con ID: " + id));

        if (!materiaToDelete.getSemestre().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("Acceso Denegado: No tienes permiso para eliminar la materia con ID: " + id);
        }
        materiaRepository.delete(materiaToDelete);
    }
}
