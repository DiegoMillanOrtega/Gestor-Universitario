package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findAllByMateria_Semestre_UsuarioId(Long usuarioId);
    Optional<Nota> findByIdAndMateria_Semestre_UsuarioId(Long id, Long usuarioId);
}
