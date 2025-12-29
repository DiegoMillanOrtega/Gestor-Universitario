import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MateriaInterface, MateriaRequestInterface } from '@/interface/materia.interface';

@Injectable({providedIn: 'root'})
export class MateriaService {
    
    
    private baseUrl = 'http://localhost:8080/api/materias';
    private http = inject(HttpClient);

    constructor() { }

    getAllMaterias() {
        return this.http.get<MateriaInterface[]>(this.baseUrl);
    }

    guardarMateria(materia: MateriaRequestInterface) {
        return this.http.post(this.baseUrl, materia);
    }
    
    eliminarMateria(id: string) {
        return this.http.delete(`${this.baseUrl}/${id}`);
    }
}