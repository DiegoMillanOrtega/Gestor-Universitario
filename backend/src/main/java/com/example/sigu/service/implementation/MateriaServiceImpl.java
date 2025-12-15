package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.repository.IMateriaRepository;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import com.example.sigu.service.exception.SemestreNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import com.example.sigu.util.mapper.MateriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements IMateriaService {


    private final IMateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;

    @Override
    public List<MateriaResponse> findAll() {
        return materiaRepository.findAll()
                .stream()
                .map(materiaMapper::toMateriaResponse)
                .toList();
    }

    @Override
    public Optional<MateriaResponse> findById(Long id) {
        return materiaRepository.findById(id).map(materiaMapper::toMateriaResponse);
    }

    @Override
    public Materia save(MateriaRequest materiaRequest) {
        return materiaRepository.save(materiaMapper.toMateria(materiaRequest));
    }

    @Override
    public void delete(Long id) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new SemestreNotFoundException("Materia no encontrada con ID: " + id));
        materiaRepository.delete(materia);
    }
}
