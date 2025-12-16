package com.example.sigu.persistence.entity;


import com.example.sigu.persistence.enums.TipoArchivo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "archivos")
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, name = "tipo_archivo",  nullable = false)
    private TipoArchivo tipo;

    @Column(nullable = false, length = 200)
    private String url;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "materiaId", foreignKey = @ForeignKey(name = "fk_archivo_materia"),  nullable = false)
    private Materia materia;

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;
}
