import { ArchivoService } from '@/pages/service/archivo.service';
import { MateriaService } from '@/pages/service/materia.service';
import {
    ChangeDetectionStrategy,
    Component,
    computed,
    effect,
    inject,
    input,
    OnInit,
    signal,
} from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { FileUploadModule, UploadEvent } from 'primeng/fileupload';
import { TextareaModule } from 'primeng/textarea';
import { RouterLink } from '@angular/router';
import { ArchivoRequest } from '@/interface/archivo-request.interface';
import { MessageService } from 'primeng/api';
import { BlockUIModule } from 'primeng/blockui';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PanelModule } from 'primeng/panel';
import { LoadingSpinnerOverlayComponent } from '@/layout/component/app.loadingspinneroverlay';
import { LoadingComponent } from '@/layout/component/app.loading';


@Component({
    standalone: true,
    imports: [
        ReactiveFormsModule,
        ButtonModule,
        SelectModule,
        InputTextModule,
        FileUploadModule,
        TextareaModule,
        RouterLink,
        BlockUIModule,
        ProgressSpinnerModule,
        PanelModule,
        LoadingSpinnerOverlayComponent,
        LoadingComponent
    ],
    selector: 'app-archivo-form',
    templateUrl: 'archivo-form.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class ArchivoForm {
    //Providers
    private materiaService = inject(MateriaService);
    private archivoService = inject(ArchivoService);
    private messageService = inject(MessageService);
    private fb = inject(FormBuilder);

    //Variables
    readonly id = input<string>('');
    selectedFile = signal<File | null>(null);
    formEnviado = signal(false);
    loading = signal(false);

    //Resources
    materias = this.materiaService.getAllMateriasBySemestreActivo();
    archivoMetadata = this.archivoService.getArchivo(this.id);
    
    //Computed
    isFileValid = computed(() => {
        const file = this.selectedFile();
        const id = this.id();
        if (id) return true;
        return !!file && file.size > 0;
    });
    
    //Forms
    formArchivo = this.fb.group({
        nombre: [''],
        descripcion: [''],
        materiaId: ['', Validators.required],
    });
    
    
    constructor() {
        effect(() => {
            const archivo = this.archivoMetadata.value();
            
            if (archivo) {
                this.formArchivo.patchValue({
                    ...archivo,
                    materiaId: archivo.materia.id,
                }, { emitEvent: false });
            }
        });
    }

    controlInvalido(name: string): boolean {
        const control = this.formArchivo.get(name);
        return !!(control?.invalid && (control.touched || this.formEnviado()));
    }

    onFileChange(event: any) {
        this.selectedFile.set(event.currentFiles[0]);
    }


    onSubmit() {
        this.formEnviado.set(true);
        const file = this.selectedFile();

        if (this.formArchivo.invalid || !this.isFileValid()) return;

        const id = this.id();

        // Validación de negocio: Si no hay ID, el archivo es obligatorio
        if (!id && !file) {
            this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'El archivo es obligatorio para nuevos registros.',
            });
            return;
        }

        const payload = this.formArchivo.getRawValue() as ArchivoRequest;
        this.loading.set(true);

        const request$ = id
            ? this.archivoService.patchArchivo(id, payload, file)
            : this.archivoService.saveArchivo(payload, file!);

        request$.subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: `Archivo ${id ? 'actualizado' : 'subido'} correctamente`,
                    life: 3000,
                });
            },
            error: (err) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail:
                        err.error?.message || 'Error al procesar la solicitud',
                });
                this.loading.set(false);
            },
            complete: () => {
                this.formEnviado.set(false);
                this.loading.set(false);
            },
        });
    }
}
