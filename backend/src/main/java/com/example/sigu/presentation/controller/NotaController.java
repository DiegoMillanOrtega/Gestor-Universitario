package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaResponse;
import com.example.sigu.service.interfaces.INotaService;
import com.example.sigu.util.mapper.NotaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
                .map(mapper::toNotaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::toNotaResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NotaResponse> save(@Valid @RequestBody NotaRequest request){
        Nota notaSaved = service.save(request);
        return ResponseEntity
                .created(URI.create("/api/notas/" + notaSaved.getId()))
                .body(mapper.toNotaResponse(notaSaved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
