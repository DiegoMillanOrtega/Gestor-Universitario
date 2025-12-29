package com.example.sigu.persistence.entity;


import com.example.sigu.persistence.enums.EstadoMateria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;
    private String nombre;
    private int numCreditos;
    private String profesor;
    private String horario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semestre_id", foreignKey = @ForeignKey(name = "fk_materia_semestre"))
    private Semestre semestre;

    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p1 = BigDecimal.ZERO;

    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p2 = BigDecimal.ZERO;

    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p3  = BigDecimal.ZERO;

    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal ex = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private EstadoMateria estado;


    @Transient
    public BigDecimal getPromedio() {
        //Promedio de parciales (p1+p2+p3) / 3
        BigDecimal sumaParciales = p1.add(p2).add(p3);
        BigDecimal promedioParciales = sumaParciales.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);

        //Aplicar porcentajes: (Parciales * 70%) + (Examen * 30%)
        BigDecimal parteParciales = promedioParciales.multiply(BigDecimal.valueOf(0.7));
        BigDecimal parteExamen = ex.multiply(BigDecimal.valueOf(0.3));

        return parteParciales.add(parteExamen).setScale(2, RoundingMode.HALF_UP);
    }
}
