package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaResponse;
import com.example.sigu.presentation.dto.nota.NotaUpdateRequest;
import com.example.sigu.service.interfaces.INotaService;
import com.example.sigu.util.mapper.NotaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final INotaService service;
    private final NotaMapper mapper;

    @GetMapping
    public List<NotaResponse> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.findById(id)));
    }

    @GetMapping("/{id}/proyeccion")
    public ResponseEntity<Map<String, BigDecimal>> calcularRequerimiento(@PathVariable Long id) {
        return ResponseEntity.ok(service.calcularRequerimiento(id));
    }

    @PostMapping
    public ResponseEntity<NotaResponse> save(@Valid @RequestBody NotaRequest request){
        Nota notaSaved = service.save(request);
        return ResponseEntity
                .created(URI.create("/api/notas/" + notaSaved.getId()))
                .body(mapper.toResponse(notaSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponse> update(@PathVariable Long id, @Valid @RequestBody NotaUpdateRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.update(id, request)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
