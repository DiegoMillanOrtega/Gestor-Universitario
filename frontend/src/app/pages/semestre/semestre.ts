import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {
    ChangeDetectionStrategy,
    Component,
    computed,
    inject,
    signal,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { SelectModule } from 'primeng/select';
import { SemestreService } from '../service/semestre.service';
import { FormsModule } from '@angular/forms';
import { SemestreCard } from './components/semestre-card';
import { EmptyState } from './components/empty-state';
import { Router, RouterLink } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BadgeModule } from 'primeng/badge';
import { TagModule } from 'primeng/tag';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToolbarModule } from 'primeng/toolbar';

export interface FilterStatusOptions {
    label: string;
    value: string;
    severity: string;
}

export type SortOption = 'fecha' | 'promedio';
export type StatusOption = 'All' | 'ACTIVO' | 'FINALIZADO' | 'PLANIFICADO';

@Component({
    selector: 'app-semestre',
    standalone: true,
    imports: [
        SelectModule,
        ButtonModule,
        FormsModule,
        SemestreCard,
        EmptyState,
        ToastModule,
        ConfirmDialogModule,
        RouterLink,
        BadgeModule,
        TagModule,
        ProgressSpinnerModule,
        ToolbarModule,
    ],
    templateUrl: 'semestre.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class Semestre {
    //Providers
    private router = inject(Router);
    private confirmationService = inject(ConfirmationService);
    private messageService = inject(MessageService);
    private semestreService = inject(SemestreService);

    //Variables
    sortBy = signal<SortOption>('fecha');
    filterStatus = signal<StatusOption>('All');

    //OptionsSelect
    filterStatusOptions: FilterStatusOptions[] = [
        {
            label: 'Todos los estados',
            value: 'All',
            severity: 'secondary',
        },
        {
            label: 'Activo',
            value: 'ACTIVO',
            severity: 'success',
        },
        {
            label: 'Finalizado',
            value: 'FINALIZADO',
            severity: 'secondary',
        },
        {
            label: 'Planificado',
            value: 'PLANIFICADO',
            severity: 'warn',
        },
    ];

    sortByOptions: {}[] = [
        { label: 'Mas recientes', value: 'fecha' },
        { label: 'Mejor promedio', value: 'promedio' },
    ];

    //Resources
    semestresResource = this.semestreService.getAllSemestres();

    semestresFiltrados = computed(() => {
        const data = this.semestresResource.value() ?? [];
        console.log(data);

        const estado = this.filterStatus();
        const orden = this.sortBy();

        let resultado =
            estado === 'All' ? data : data.filter((s) => s.estado === estado);

        return [...resultado].sort((a, b) => {
            if (orden === 'promedio') {
                return (b.promedioActual ?? 0) - (a.promedioActual ?? 0);
            }

            return (b.fechaInicio ?? '').localeCompare(a.fechaInicio ?? '');
        });
    });

    agregarSemestre() {
        this.router.navigate(['/pages/semestres/agregar']);
    }

    limpiarFiltro() {
        this.filterStatus.set('All');
        this.sortBy.set('fecha');
    }

    eliminarSemestre(id: string) {
        this.confirmationService.confirm({
            key: 'globalConfirm',
            message:
                '¿Está seguro de que desea eliminar este semestre? Esta acción no se puede deshacer.',
            header: 'Confirmar eliminación',
            icon: 'pi pi-exclamation-triangle',
            acceptButtonProps: { severity: 'danger' },
            rejectButtonProps: { severity: 'secondary' },
            accept: () => {
                this.semestreService.eliminarSemestre(id).subscribe({
                    next: () => {
                        this.semestresResource.reload();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Eliminado',
                            detail: 'Registro borrado con éxito',
                        });
                    },
                    error: (err) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail: err.error?.message || 'No se pudo eliminar',
                        });
                    },
                });
            },
        });
    }
}
