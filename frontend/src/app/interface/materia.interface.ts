import { SemestreInterface } from "./semestre.interface";

export interface MateriaInterface {
    id: string;
    nombre: string;
    codigo: string;
    semestre: SemestreInterface;
    estado: MateriaStatus;
    numCreditos: number;
    profesor: string;
    horario: string;
    p1: number | null;
    p2: number | null;
    p3: number | null;
    ex: number | null;
    promedio: number | null;
}

export interface MateriaRequestInterface {
    nombre: string;
    codigo: string;
    semestreId: string;
    estado: MateriaStatus;
    numCreditos: number;
    profesor: string;
    horario: string;
}


export interface MateriaInterfaceFiltradas {
    id: string;
    nombre: string;
    codigo: string;
    semestre: SemestreInterface;
    estado: string;
    numCreditos: number;
    profesor: string;
    horario: string;
    p1: number;
    p2: number;
    p3: number;
    ex: number;
    promedio: number;
    totalMateriasFiltradas: number;
    totalCreditosFiltrados: number;
    totalMateriasActivasFiltradas: number;
}

export type MateriaStatus = 'ACTIVA' | 'COMPLETADA' | 'CANCELADA';