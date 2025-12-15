package com.example.sigu.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int numCreditos;
    private String profesor;
    private String horario;
    @ManyToOne
    @JoinColumn(name = "semestre_id", foreignKey = @ForeignKey(name = "fk_materia_semestre"))
    private Semestre semestre;
}
