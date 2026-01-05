import {
    ChangeDetectionStrategy,
    Component,
    input,
    Input,
} from '@angular/core';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
    selector: 'app-loading-spinner-overlay',
    imports: [ProgressSpinnerModule],
    template: `
        @if (isLoading()) {
            <div class="loading-overlay">
                <div class="spinner-container">
                    <p-progress-spinner
                        strokeWidth="8"
                        fill="transparent"
                        animationDuration=".5s"
                        [style]="{ width: '50px', height: '50px' }"
                    />
                    <p>Cargando...</p>
                </div>
            </div>
        }
    `,
    styles: [
        `
            .loading-overlay {
                /* Posicionamiento fijo para cubrir toda la pantalla */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;

                /* Fondo oscuro traslúcido */
                background-color: rgba(0, 0, 0, 0.5);

                /* Flexbox para centrar el contenido */
                display: flex;
                justify-content: center;
                align-items: center;

                /* Asegurar que esté por encima de todo (Z-index alto) */
                z-index: 9999;

                /* Suavizar la transición */
                transition: opacity 0.3s ease-in-out;
            }

            .spinner-container {
                text-align: center;
                color: white;
            }

            .spinner-container p {
                margin-top: 10px;
                font-weight: 500;
            }
        `,
    ],
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoadingSpinnerOverlayComponent {
    // Puedes controlar la visibilidad desde afuera
    isLoading = input<boolean>(false);
}
