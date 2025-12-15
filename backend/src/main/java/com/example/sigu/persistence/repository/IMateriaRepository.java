package com.example.sigu.persistence.repository;

import com.example.sigu.persistence.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia,Long> {

}
