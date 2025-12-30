export interface SemestreInterface {
    id:             string;
    nombre:         string;
    anio:           number;
    fechaInicio:    string;
    fechaFin:       string;
    estado:         SemestreStatus;
    cargaAcademica:  CargaAcademica;
    numeroMaterias: number;
    creditosTotales: number;
    progreso:        number;
    promedioActual:  number;
    materiasAprobadas: number;
    materiasEnCurso: number;
    materiasPendientes: number;
    usuarioId:        string;
}


export type SemestreStatus = 'ACTIVO' | 'FINALIZADO' | 'PLANIFICADO';
export type CargaAcademica = "BAJA" | "MEDIA" | "ALTA"