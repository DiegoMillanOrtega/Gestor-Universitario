import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { CardModule } from 'primeng/card';

@Component({
    selector: 'app-estadisticas-materias', // Nombre en español
    standalone: true,
    imports: [CardModule],
    templateUrl: 'estadisticas-materias.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})

export class EstadisticasMaterias{

    //Inputs
    total = input<number>(0);
    creditos = input<number>(0);
    activas = input<number>(0);
}