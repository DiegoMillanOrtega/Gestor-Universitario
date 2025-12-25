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
}