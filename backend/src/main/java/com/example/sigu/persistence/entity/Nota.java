package com.example.sigu.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "notas")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p1 = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p2 = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal p3  = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 2, scale = 1, nullable = false)
    private BigDecimal ex = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "materia_id", foreignKey = @ForeignKey(name = "fk_nota_materia"), nullable = false)
    private Materia materia;

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