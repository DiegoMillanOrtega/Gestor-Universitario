package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findAllByMateria_Semestre_UsuarioId(Long materiaId);
    Optional<Tarea> findByIdAndMateria_Semestre_UsuarioId(Long id, Long materiaId);
}
