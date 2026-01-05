import { inject, Injectable, Signal } from '@angular/core';
import { HttpClient, httpResource, HttpResourceRef } from '@angular/common/http';
import { MateriaInterface, MateriaRequestInterface } from '@/interface/materia.interface';


@Injectable({providedIn: 'root'})
export class MateriaService {
    
    
    private baseUrl = 'http://localhost:8080/api/materias';
    private http = inject(HttpClient);

    constructor() { }

    getAllMaterias(): HttpResourceRef<MateriaInterface[] | undefined> {
        return httpResource<MateriaInterface[]>(() => 'http://localhost:8080/api/materias');
    }

    getAllMateriasBySemestreActivo(): HttpResourceRef<MateriaInterface[] | undefined> {
        return httpResource<MateriaInterface[]>(() => 'http://localhost:8080/api/materias/semestre-activo');
    }

    getMateria(id: Signal<string>): HttpResourceRef<MateriaInterface | undefined> {
        return httpResource<MateriaInterface>(() => {
            const idValue = id();
            return idValue ? `${this.baseUrl}/${idValue}` : undefined;
        });
    }

    guardarMateria(materia: MateriaRequestInterface) {
        return this.http.post(this.baseUrl, materia);
    }
    
    eliminarMateria(id: string) {
        return this.http.delete(`${this.baseUrl}/${id}`);
    }
}