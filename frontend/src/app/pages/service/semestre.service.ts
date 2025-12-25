import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SemestreInterface } from '@/interface/semestre.interface';

@Injectable({providedIn: 'root'})
export class SemestreService {
    
    
    private http = inject(HttpClient);
    private baseUrl = 'http://localhost:8080/api/semestres';
    
    getAllSemestres(): Observable<SemestreInterface[]> {
        return this.http.get<SemestreInterface[]>(`${this.baseUrl}`);
    }
    
    obtenerSemestrePorId(id: string) {
        return this.http.get<SemestreInterface>(`${this.baseUrl}/${id}`);
    }

    guardaSemestre(semestre: SemestreInterface) {
        return this.http.post<SemestreInterface>(`${this.baseUrl}`, semestre);
    }
    
    eliminarSemestre(id: string) {
        return this.http.delete<SemestreInterface>(`${this.baseUrl}/${id}`);
    }
}