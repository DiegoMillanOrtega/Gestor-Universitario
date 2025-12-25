import { CargaAcademica, SemestreInterface, SemestreStatus } from '@/interface/semestre.interface';
import { Component, computed, inject, input, OnInit, output } from '@angular/core';
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


@Component({
    selector: 'app-semestre-card',
    imports: [CardModule, BadgeModule, OverlayBadgeModule, MenuModule, ButtonModule, ProgressBarModule, DividerModule, CommonModule],
    templateUrl: 'semestre-card.html'
})
export class SemestreCard implements OnInit {
    private router = inject(Router);

    semestre = input.required<SemestreInterface>();
    onDelete = output<string>();

    private statusConfig: Record<SemestreStatus, { label: string; className: string }> = {
        ACTIVO: {
            label: 'Activo',
            className: '!bg-green-700 !font-extrabold'
        },
        FINALIZADO: {
            label: 'Finalizado',
            className: '!bg-gray-300 dark:!bg-gray-700 !text-black dark:!text-white !font-extrabold' 
        },
        PLANIFICADO: {
            label: 'Planificado',
            className: '!bg-yellow-600 !text-black dark:!text-white !font-extrabold'
        }
    };

    private cargaConfig: Record<CargaAcademica, { label: string; color: string }> = {
        BAJA: {
            label: 'Carga baja',
            color: 'font-bold text-green-700'
        },
        MEDIA: {
            label: 'Carga media',
            color: 'font-bold text-yellow-600'
        },
        ALTA: {
            label: 'Carga alta',
            color: 'font-bold text-red-500'
        }
    };

    menuItemsOptions: MenuItem[] = [
        {
            label: 'Editar',
            icon: 'pi pi-fw pi-pencil',
            command: () => {
                this.router.navigate(['/pages/semestres/editar', this.semestre().id]);
            }
        },
        {
            separator: true
        },
        {
            label: 'Eliminar',
            icon: 'pi pi-fw pi-trash',
            linkClass: '!text-red-500 dark:!text-red-400',
            command: (event: any) => {
                this.onDelete.emit(this.semestre().id);
            }
        }
    ];

    statusInfo = computed(() => this.statusConfig[this.semestre().estado]);
    cargaInfo = computed(() => this.cargaConfig[this.semestre().cargaAcademica]);
    ngOnInit() {}
}
