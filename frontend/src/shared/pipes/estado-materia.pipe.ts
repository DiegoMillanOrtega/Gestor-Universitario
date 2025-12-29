import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'materiaEstadoColor',
    standalone: true,
})
export class MateriaEstadoColorPipe implements PipeTransform {
    transform(estado: string | undefined): string {
        const colors: Record<string, string> = {
            ACTIVA: '!bg-emerald-500/10 !text-emerald-500 !border-emerald-500/20',
            COMPLETADA: '!bg-blue-500/10 !text-blue-500 !border-blue-500/20',
            CANCELADA:
                '!bg-destructive/10 !text-destructive !border-destructive/20',
        };
        return colors[estado ?? ''] ?? 'text-red-500';
    }
}
