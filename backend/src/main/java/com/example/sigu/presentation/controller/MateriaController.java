package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import com.example.sigu.service.interfaces.IMateriaService;
import com.example.sigu.util.mapper.MateriaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final IMateriaService materiaService;
    private final MateriaMapper materiaMapper;

    @GetMapping
    public List<MateriaResponse> findAll() {
        return materiaService.findAll()
                .stream()
                .map(materiaMapper::toMateriaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> findById(@PathVariable Long id) {
        return materiaService.findById(id)
                .map(materiaMapper::toMateriaResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Materia> guardar(@Valid @RequestBody MateriaRequest materia) {
        Materia savedMateria = materiaService.save(materia);

        return ResponseEntity
                .created(URI.create("/api/materias/" + savedMateria.getId()))
                .body(savedMateria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws AccessDeniedException {
        materiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

