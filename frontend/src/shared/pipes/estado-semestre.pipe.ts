import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'estadoSemestreColor',
})

export class EstadoSemestreColorPipe implements PipeTransform {
    transform(estado: string | undefined): string {
        const colors: Record<string, string> = {
            ACTIVO: '!text-green-500',
            FINALIZADO: '!text-gray-500',
            PLANIFICADO: '!text-yellow-500',
        };
        return colors[estado ?? ''] || '';
    }
}