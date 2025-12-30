import {
    CargaAcademica,
    SemestreInterface,
    SemestreStatus,
} from '@/interface/semestre.interface';
import {
    ChangeDetectionStrategy,
    Component,
    computed,
    inject,
    input,
    output,
} from '@angular/core';
import { CardModule } from 'primeng/card';
import { BadgeModule } from 'primeng/badge';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import { MenuItem } from 'primeng/api';
import { ProgressBarModule } from 'primeng/progressbar';
import { DividerModule } from 'primeng/divider';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TagModule } from 'primeng/tag';


const STATUS_CONFIG: Record<SemestreStatus, { label: string; severity: string }> = {
    ACTIVO: { label: 'Activo', severity: 'success' },
    FINALIZADO: { label: 'Finalizado', severity: 'secondary' },
    PLANIFICADO: { label: 'Planificado', severity: 'warn' },
};

const CARGA_CONFIG: Record<CargaAcademica, { label: string; color: string }> = {
    BAJA: { label: 'Carga baja', color: 'text-green-600 dark:text-green-400' },
    MEDIA: { label: 'Carga media', color: 'text-yellow-600 dark:text-yellow-400' },
    ALTA: { label: 'Carga alta', color: 'text-red-500 dark:text-red-400' },
};

@Component({
    selector: 'app-semestre-card',
    standalone: true,
    imports: [
        CardModule,
        BadgeModule,
        OverlayBadgeModule,
        MenuModule,
        ButtonModule,
        ProgressBarModule,
        DividerModule,
        CommonModule,
        TagModule
    ],
    templateUrl: 'semestre-card.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SemestreCard {
    private router = inject(Router);

    semestre = input.required<SemestreInterface>();
    onDelete = output<string>();

    //Computed
    statusInfo = computed(() => STATUS_CONFIG[this.semestre().estado]);
    cargaInfo = computed(() => CARGA_CONFIG[this.semestre().cargaAcademica]);

    menuItemsOptions = computed<MenuItem[]>(() => [
        {
            label: 'Editar',
            icon: 'pi pi-pencil',
            command: () => this.router.navigate(['/pages/semestres/editar', this.semestre().id]),
        },
        { separator: true },
        {
            label: 'Eliminar',
            icon: 'pi pi-trash',
            itemStyle: { color: '#ef4444' }, // Rojo esmeralda
            command: () => this.onDelete.emit(this.semestre().id),
        },
    ]);
}
