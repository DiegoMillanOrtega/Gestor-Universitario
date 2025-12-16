package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Tarea;
import com.example.sigu.presentation.dto.tarea.TareaRequest;
import com.example.sigu.presentation.dto.tarea.TareaResponse;
import com.example.sigu.service.interfaces.ITareaService;
import com.example.sigu.util.mapper.TareaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final ITareaService service;
    private final TareaMapper mapper;

    @GetMapping
    public List<TareaResponse> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toTareaResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> findById(@PathVariable Long id){
        return service.findById(id)
                .map(mapper::toTareaResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TareaResponse> save(@Valid @RequestBody TareaRequest request){
        Tarea tareaSaved = service.save(request);
        return ResponseEntity
                .created(URI.create("/api/tareas/"+ tareaSaved.getId()))
                .body(mapper.toTareaResponse(tareaSaved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
