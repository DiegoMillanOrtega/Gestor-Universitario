package com.example.sigu.presentation.controller;


import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.presentation.dto.semestre.SemestreRequest;
import com.example.sigu.presentation.dto.semestre.SemestreResponse;
import com.example.sigu.service.interfaces.ISemestreService;
import com.example.sigu.util.mapper.SemestreMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {

    private final ISemestreService semestreService;
    private final SemestreMapper semestreMapper;

    @GetMapping
    public List<SemestreResponse> findAll() {
        return semestreService.findAll()
                .stream()
                .map(semestreMapper::toSemestreResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemestreResponse> findById(@PathVariable Long id) {
        return semestreService.findById(id)
                .map(semestreMapper::toSemestreResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SemestreResponse> save(@Valid @RequestBody SemestreRequest request) {
        Semestre semestre = semestreService.save(request);

        return ResponseEntity
                .created(URI.create("/api/materias/"+ semestre.getId()))
                .body(semestreMapper.toSemestreResponse(semestre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        semestreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
