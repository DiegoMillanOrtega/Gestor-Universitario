import {
    ChangeDetectionStrategy,
    Component,
    inject,
    signal,
} from '@angular/core';
import { ArchivoService } from '../service/archivo.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { MenuModule } from 'primeng/menu';
import { ArchivoTable } from './components/archivo-table';
import { DialogModule } from 'primeng/dialog';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LoadingSpinnerOverlayComponent } from '@/layout/component/app.loadingspinneroverlay';
import { LoadingComponent } from '@/layout/component/app.loading';

export type SortOption = 'fechaReciente' | 'fechaAntiguo';

@Component({
    selector: 'app-archivo',
    standalone: true,
    imports: [
        ProgressSpinnerModule,
        TableModule,
        ToolbarModule,
        ButtonModule,
        IconFieldModule,
        InputIconModule,
        InputTextModule,
        SelectModule,
        FormsModule,
        MenuModule,
        ArchivoTable,
        DialogModule,
        LoadingSpinnerOverlayComponent,
        LoadingComponent,
    ],
    templateUrl: 'archivo.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class Archivo {
    private archivoService = inject(ArchivoService);
    private messageService = inject(MessageService);
    private router = inject(Router);
    private confirmationService = inject(ConfirmationService);

    archivosResource = this.archivoService.getAllArchivos();

    //Signals
    sortBy = signal<SortOption>('fechaReciente');
    showVisibleForm = signal(false);
    loading = signal(false);

    //Options Select
    optionsSortBy = [
        { label: 'Más reciente', value: 'fechaReciente' },
        { label: 'Más antiguo', value: 'fechaAntiguo' },
    ];

    constructor() {}

    abrirEnlanceArchivo(url: string) {
        window.open(url, '_blank');
    }

    editarArchivo(id: string) {
        this.router.navigate([`/pages/archivos/editar/${id}`]);
    }

    eliminarArchivo(id: string) {
        this.confirmationService.confirm({
            key: 'globalConfirm',
            target: event?.target as HTMLElement,
            message: `¿Está seguro de que desea eliminar el archivo ${id}? Esta acción no se puede deshacer.`,
            header: '¿Eliminar archivo?',
            rejectLabel: 'Cancelar',
            rejectButtonProps: {
                label: 'Cancelar',
                icon: 'pi pi-times',
                severity: 'secondary',
                outlined: true,
            },
            acceptButtonProps: {
                label: 'Eliminar',
                severity: 'danger',
                icon: 'pi pi-trash',
            },
            accept: () => {
                this.loading.set(true);

                this.archivoService.deleteArchivo(id).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Archivo eliminado',
                            detail: 'El archivo ha sido eliminado correctamente.',
                        });
                        this.archivosResource.reload();
                    },
                    error: (err) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                err.error?.message ||
                                'Error al procesar la solicitud',
                        });
                        this.loading.set(false);
                    },
                    complete: () => {
                        this.loading.set(false);
                    },
                });
            },
            reject: () => {
                this.messageService.add({
                    severity: 'info',
                    summary: 'Archivo no eliminado',
                    detail: 'El archivo no ha sido eliminado.',
                });
            }
        });
    }
}
