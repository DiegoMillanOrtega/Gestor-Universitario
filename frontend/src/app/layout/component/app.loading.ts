import { Component, OnInit } from '@angular/core';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
    selector: 'app-loading',
    standalone: true,
    imports: [ProgressSpinnerModule],
    template: `
        <div class="flex flex-col items-center justify-center py-20">
            <p-progress-spinner styleClass="w-12 h-12" strokeWidth="4" />
            <p class="mt-4 text-muted-foreground animate-pulse">
                Cargando archivos...
            </p>
        </div>
    `,
})
export class LoadingComponent {
}
