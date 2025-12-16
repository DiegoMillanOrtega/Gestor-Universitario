package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Parcial;
import com.example.sigu.persistence.repository.IParcialRepository;
import com.example.sigu.presentation.dto.parcial.ParcialRequest;
import com.example.sigu.presentation.dto.parcial.ParcialResponse;
import com.example.sigu.service.exception.ParcialNotFoundException;
import com.example.sigu.service.interfaces.IParcialService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.ParcialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParcialServiceImpl implements IParcialService {

    private final IParcialRepository repository;
    private final ParcialMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public List<Parcial> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public Optional<Parcial> findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public Parcial save(ParcialRequest request) {
        return repository.save(mapper.toParcial(request));

    }

    @Override
    public void delete(Long id) {
        Parcial parcialToDelete = findById(id)
                .orElseThrow(() -> new ParcialNotFoundException("El parcial con ID: " + id + " no fue encontrado"));

        repository.delete(parcialToDelete);
    }
}
