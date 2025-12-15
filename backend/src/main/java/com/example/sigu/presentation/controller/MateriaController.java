package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.presentation.dto.materia.MateriaRequest;
import com.example.sigu.presentation.dto.materia.MateriaResponse;
import com.example.sigu.service.interfaces.IMateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private IMateriaService materiaService;

    @GetMapping
    public List<MateriaResponse> findAll() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> findById(@PathVariable Long id) {
        return materiaService.findById(id)
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

