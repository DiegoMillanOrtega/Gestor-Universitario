package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ISemestreRepository extends JpaRepository<Semestre,Long> {

    List<Semestre> findAllByUsuarioId(Long usuarioId);
    Optional<Semestre> findByIdAndUsuarioId(Long semestreId,Long usuarioId);

    @Query("""
           SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END 
           FROM Semestre s
           WHERE 
               (:currentId IS NULL OR s.id != :currentId) AND 
               s.fechaInicio <= :newFechaFin AND 
               s.fechaFin >= :newFechaInicio AND
               s.usuario.id =:userId
           """)
    boolean existsOverlap(
            @Param("newFechaInicio") LocalDate newFechaInicio,
            @Param("newFechaFin") LocalDate newFechaFin,
            @Param("currentId") Long currentId,
            @Param("userId") Long userId
    );
}
