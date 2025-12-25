import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { SelectModule } from 'primeng/select';
import { SemestreService } from '../service/semestre.service';
import { SemestreInterface } from '@/interface/semestre.interface';
import { FormsModule } from '@angular/forms';
import { SemestreCard } from './components/semestre-card';
import { EmptyState } from './components/empty-state';
import { Router, RouterLink } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BadgeModule } from 'primeng/badge';

export interface FilterStatusOptions {
    label: string;
    value: string;
    className: string;
}

@Component({
    selector: 'app-semestre',
    imports: [SelectModule, ButtonModule, FormsModule, SemestreCard, EmptyState, ToastModule, ConfirmDialogModule, RouterLink, BadgeModule],
    templateUrl: 'semestre.html'
})
export class Semestre implements OnInit {
    private semestreService = inject(SemestreService);
    private router = inject(Router);
    private messageService = inject(MessageService);
    private confirmationService = inject(ConfirmationService);

    semestres = signal<SemestreInterface[]>([]);

    sortBy = signal<string>('fecha');
    sortByOptions: {}[] = [
        { label: 'Mas recientes', value: 'fecha' },
        { label: 'Mejor promedio', value: 'promedio' }
    ];
    filterStatus = signal<string>('All');
    filterStatusOptions: FilterStatusOptions[] = [];

    semestresfiltrados = computed(() => {
        // 1. Obtener los valores actuales de las señales
        const listaOriginal = this.semestres();
        const filtro = this.filterStatus();
        const orden = this.sortBy();

        // 2. Filtrar
        let resultado = listaOriginal.filter((s) => filtro === 'All' || s.estado === filtro);

        // 3. Ordenar (hacemos una copia con [...] para no mutar el original si fuera necesario)
        return [...resultado].sort((a, b) => {
            if (orden === 'promedio') {
                return (b.promedioActual ?? 0) - (a.promedioActual ?? 0);
            }

            if (orden === 'fecha') {
                const fechaA = new Date(a.fechaInicio).getTime();
                const fechaB = new Date(b.fechaInicio).getTime();
                return fechaB - fechaA; // Más recientes primero
            }

            return 0;
        });
    });

    ngOnInit(): void {
        this.filterStatusOptions = [
            {
                label: 'Todos los estados',
                value: 'All',
                className: '!bg-gray-300 dark:!bg-gray-700 !text-black dark:!text-white '
            },
            {
                label: 'Activo',
                value: 'ACTIVO',
                className: '!bg-green-700'
            },
            {
                label: 'Finalizado',
                value: 'FINALIZADO',
                className: '!bg-gray-300 dark:!bg-gray-700 !text-black dark:!text-white' 
            },
            {
                label: 'Planificado',
                value: 'PLANIFICADO',
                className: '!bg-yellow-600 !text-black dark:!text-white '
            }
        ];

        this.obtenerSemestres();
    }

    obtenerSemestres() {
        this.semestreService.getAllSemestres().subscribe((semestres) => {
            this.semestres.set(semestres);
        });
    }

    agregarSemestre() {
        this.router.navigate(['/pages/semestres/agregar']);
    }

    eliminarSemestre(id: string) {
        this.confirmationService.confirm({
            key: 'globalConfirm',
            target: event?.target as HTMLElement,
            message: '¿Está seguro de que desea eliminar este semestre?',
            header: 'Eliminar semestre',
            icon: 'pi pi-info-circle',
            rejectLabel: 'Cancelar',
            rejectButtonProps: {
                label: 'Cancelar',
                icon: 'pi pi-times',
                severity: 'secondary',
                outlined: true
            },
            acceptButtonProps: {
                label: 'Eliminar',
                severity: 'danger',
                icon: 'pi pi-trash'
            },
            accept: () => {
                this.semestreService.eliminarSemestre(id).subscribe({
                    next: () => {
                        // 1. Actualizamos la lista local (usando tus Signals de Angular)
                        this.semestres.set(this.semestres().filter((s) => s.id !== id));

                        // 2. Mostramos el mensaje de éxito
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Eliminado',
                            detail: 'El semestre ha sido eliminado correctamente.',
                            life: 3000
                        });
                    },
                    error: (err) => {
                        // Es buena práctica manejar errores aquí
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail: err.error.message,
                            life: 5000
                        });
                    }
                });
            }
        });
    }
}
