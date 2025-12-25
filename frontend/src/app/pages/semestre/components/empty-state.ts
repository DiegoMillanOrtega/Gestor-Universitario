import { Component, output } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
    selector: 'app-empty-state',
    imports: [ButtonModule],
    templateUrl: 'empty-state.html'
})

export class EmptyState {
    constructor() { }

    agregarSemestre = output();

}