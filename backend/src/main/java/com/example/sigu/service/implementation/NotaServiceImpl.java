package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.persistence.repository.INotaRepository;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.service.exception.NotaNotFoundException;
import com.example.sigu.service.interfaces.INotaService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.NotaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements INotaService {

    private final INotaRepository repository;
    private final SecurityUtils securityUtils;
    private final NotaMapper mapper;

    @Override
    public List<Nota> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public Optional<Nota> findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public Nota save(NotaRequest request) {
        return repository.save(mapper.toNota(request));
    }

    @Override
    public void delete(Long id) {
        Nota notaToDelete = findById(id).orElseThrow(() -> new NotaNotFoundException("Nota con ID: " + id + " no encontrada"));
        repository.delete(notaToDelete);
    }
}
