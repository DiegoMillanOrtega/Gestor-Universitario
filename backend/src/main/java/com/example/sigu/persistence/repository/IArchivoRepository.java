package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArchivoRepository extends JpaRepository<Archivo,Long> {
    List<Archivo> findAllByMateria_Semestre_UsuarioId(Long usuarioId);
    Optional<Archivo> findByIdAndMateria_Semestre_UsuarioId(Long id, Long usuarioId);
}
