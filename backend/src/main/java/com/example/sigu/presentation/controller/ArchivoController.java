package com.example.sigu.presentation.controller;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.presentation.dto.archivo.ArchivoResponse;
import com.example.sigu.service.interfaces.IArchivoService;
import com.example.sigu.util.mapper.ArchivoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoController {

    private final IArchivoService archivoService;
    private final ArchivoMapper  archivoMapper;

    @GetMapping
    public List<ArchivoResponse> findAll(){
        return archivoService.findAll()
                .stream()
                .map(archivoMapper::toArchivoResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoResponse> findById(@PathVariable Long id){
        return archivoService.findById(id)
                .map(archivoMapper::toArchivoResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArchivoResponse> create(@Valid @RequestBody ArchivoRequest request) {
        Archivo archivoCreated = archivoService.create(request);

        return ResponseEntity
                .created(URI.create("/api/archivos/" + archivoCreated.getId()))
                .body(archivoMapper.toArchivoResponse(archivoCreated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        archivoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
