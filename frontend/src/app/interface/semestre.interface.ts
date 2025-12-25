export interface SemestreInterface {
    id:             string;
    nombre:         string;
    anio:           number;
    fechaInicio:    Date;
    fechaFin:       Date;
    estado:         SemestreStatus;
    cargaAcademica:  CargaAcademica;
    numeroMaterias: number;
    creditosTotales: number;
    progreso:        number;
    // TODO: Reemplazar el tipo de dato promedioActual
    promedioActual:  number;
    materiasAprobadas: number;
    materiasEnCurso: number;
    materiasPendientes: number;
    usuarioId:        string;
}


export type SemestreStatus = 'ACTIVO' | 'FINALIZADO' | 'PLANIFICADO';
export type CargaAcademica = "BAJA" | "MEDIA" | "ALTA"