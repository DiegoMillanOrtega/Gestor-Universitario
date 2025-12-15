package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia,Long> {
    List<Materia> findAllBySemestre_UsuarioId(Long id);
    Optional<Materia> findByIdAndSemestre_UsuarioId(Long materiaId, Long usuarioId);
}
