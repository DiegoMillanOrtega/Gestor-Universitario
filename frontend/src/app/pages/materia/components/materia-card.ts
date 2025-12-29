import { MateriaInterface } from '@/interface/materia.interface';
import { Component, computed, input, OnInit, output } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MenuModule } from 'primeng/menu';
import { RippleModule } from 'primeng/ripple';
import { MateriaEstadoColorPipe } from 'src/shared/pipes/estado-materia.pipe';

@Component({
    selector: 'app-materia-card',
    imports: [
        CardModule,
        BadgeModule,
        MenuModule,
        ButtonModule,
        MateriaEstadoColorPipe,
        RippleModule
    ],
    templateUrl: 'materia-card.html'
})

export class MateriaCard {
    
    //Inputs
    materia = input.required<MateriaInterface>();

    //Outputs
    editar = output<string>();
    verDetalles = output<MateriaInterface>();
    eliminar = output<MateriaInterface>();


    //MenuItems
    menuItemsOptions = computed<MenuItem[]>(() => {
        const currentMateria = this.materia();
        
        return [
            {
                label: 'Ver detalles',
                icon: 'pi pi-fw pi-eye',
                command: () => this.verDetalles.emit(currentMateria),
            },
            {
                label: 'Editar',
                icon: 'pi pi-fw pi-pencil',
                command: () => this.editar.emit(currentMateria.id),
            },
            { separator: true },
            {
                label: 'Eliminar',
                icon: 'pi pi-fw pi-trash',
                linkClass: '!text-red-500 dark:!text-red-400',
                command: () => this.eliminar.emit(currentMateria),
            },
        ];
    });

}