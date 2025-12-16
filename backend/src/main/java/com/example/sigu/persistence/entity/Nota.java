package com.example.sigu.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Column(precision = 2, scale = 1)
    private BigDecimal p1;
    @Column(precision = 2, scale = 1)
    private BigDecimal p2;
    @Column(precision = 2, scale = 1)
    private BigDecimal p3;
    @Column(precision = 2, scale = 1)
    private BigDecimal ex;

    @ManyToOne
    @JoinColumn(name = "materia_id", foreignKey = @ForeignKey(name = "fk_nota_materia"), nullable = false)
    private Materia materia;
}