package com.example.sigu.presentation.dto.semestre;

import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.enums.Estado;
import com.example.sigu.presentation.dto.UsuarioResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemestreResponse {
    Long id;
    String nombre;
    Integer anio;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    EstadoSemestre estado;
    CargaAcademica cargaAcademica;
    Integer numeroMaterias;
    Integer creditosTotales;
    Double progreso;
    BigDecimal promedioActual;
    Integer materiasAprobadas;
    Integer materiasEnCurso;
    Integer materiasPendientes;
    UsuarioResponse usuario;
}
