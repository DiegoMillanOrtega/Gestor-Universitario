import { MateriaInterface } from "./materia.interface";

export interface ArchivoResult {
    id: string;
    nombre: string;
    mimeType: string;
    tamano: number;
    descripcion: string;
    fechaModificacion: Date;
    materia: MateriaInterface;
    googleDriveWebViewLink: string;
    googleDriveFileId: string;
}