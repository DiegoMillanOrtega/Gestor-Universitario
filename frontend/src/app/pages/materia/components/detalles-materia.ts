import { MateriaInterface } from '@/interface/materia.interface';
import { ChangeDetectionStrategy, Component, input, model, OnInit } from '@angular/core';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { MateriaEstadoColorPipe } from 'src/shared/pipes/estado-materia.pipe';

@Component({
    selector: 'app-detalles-materia',
    standalone: true,
    imports: [DialogModule, BadgeModule, MateriaEstadoColorPipe, ButtonModule],
    templateUrl: 'detalles-materia.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})

export class DetallesMateria{

    visible = model<boolean>(false);
    materiaSeleccionada = input<MateriaInterface | null>();

}