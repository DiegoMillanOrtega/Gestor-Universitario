package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia,Long> {
    List<Materia> findAllBySemestre_UsuarioId(Long usuarioId);;
    List<Materia> findAllBySemestreIdAndSemestre_UsuarioId(Long semestreId, Long usuarioId);
    Optional<Materia> findByIdAndSemestre_UsuarioId(Long materiaId, Long usuarioId);

    @Query("SELECT m FROM Materia m WHERE m.semestre.estado = 'ACTIVO'")
    List<Materia> findAllBySemestreActivo();
}
