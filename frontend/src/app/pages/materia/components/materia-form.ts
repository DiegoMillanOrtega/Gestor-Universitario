import {
    MateriaInterface,
    MateriaRequestInterface,
} from '@/interface/materia.interface';
import { httpResource } from '@angular/common/http';
import { Component, inject, signal, effect, computed, input } from '@angular/core';
import {
    FormBuilder,
    Validators,
    ɵInternalFormsSharedModule,
    ReactiveFormsModule,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { InputNumberModule } from 'primeng/inputnumber';
import { SemestreInterface } from '@/interface/semestre.interface';
import { MessageModule } from 'primeng/message';
import { ButtonModule } from 'primeng/button';
import { MateriaService } from '@/pages/service/materia.service';
import { MessageService } from 'primeng/api';
import { SemestreService } from '@/pages/service/semestre.service';

@Component({
    selector: 'app-form-materia',
    standalone: true,
    imports: [
        CardModule,
        ɵInternalFormsSharedModule,
        ReactiveFormsModule,
        InputTextModule,
        SelectModule,
        InputNumberModule,
        MessageModule,
        ButtonModule,
        RouterLink,
    ],
    templateUrl: 'materia-form.html',
})
export default class MateriaForm {
    //Providers
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private formBuilder = inject(FormBuilder);
    private messageService = inject(MessageService);
    private semestreService = inject(SemestreService);

    //Services
    private materiaService = inject(MateriaService);

    //Variables
    readonly id = input<string>('');
    formEnviado = signal(false);

    // --- Computed UI ---
    esEdicion = computed(() => !!this.id());
    headerText = computed(() =>
        this.esEdicion() ? 'Editar materia' : 'Agregar materia',
    );
    labelButton = computed(() =>
        this.esEdicion() ? 'Editar materia' : 'Crear materia',
    );

    // HttpResources
    semestreResource = this.semestreService.getAllSemestres();
    materiaResource = this.materiaService.getMateria(this.id);

    // Options for selects
    estadoMateriaOptions = [
        { label: 'Activa', value: 'ACTIVA' },
        { label: 'Completada', value: 'COMPLETADA' },
        { label: 'Cancelada', value: 'CANCELADA' },
    ];

    //forms
    formMateria = this.formBuilder.group({
        id: [null as string | null],
        nombre: ['', [Validators.required, Validators.minLength(3)]],
        codigo: ['', Validators.required],
        semestreId: ['', Validators.required],
        estado: ['ACTIVA', Validators.required],
        numCreditos: [
            <number | null>null,
            [Validators.required, Validators.min(0), Validators.max(4)],
        ],
        profesor: [''],
        horario: [''],
    });

    constructor() {
        effect(() => {
            const materia = this.materiaResource.value();
            const semestres = this.semestreResource.value();
            console.log('materia', materia);
            console.log('semestres: ', semestres);
            
            

            if (materia) {
                console.log('llgue');
                this.formMateria.patchValue({
                    ...materia,
                    semestreId: materia.semestre.id,
                });
            }
        });

        // effect(() => {
        //     const semestres = this.semestreResource.value();
        //     if (semestres && !this.esEdicion()) {
        //         const activo = semestres.find((s) => s.estado === 'ACTIVO');
        //         if (activo)
        //             this.formMateria.controls.semestreId.setValue(activo.id);
        //     }
        // });
    }

    controlInvalido(name: string): boolean {
        const control = this.formMateria.get(name);
        return !!(control?.invalid && (control.touched || this.formEnviado()));
    }

    onSubmit() {
        this.formEnviado.set(true);
        if (this.formMateria.invalid) return;

        const payload =
            this.formMateria.getRawValue() as MateriaRequestInterface;
        

        this.materiaService.guardarMateria(payload).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Éxito',
                    detail: `Materia ${this.esEdicion() ? 'actualizada' : 'creada'} correctamente`,
                });
                this.router.navigate(['/pages/materias']);
            },
            error: (err) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: err.error?.message || 'Error al procesar la solicitud',
                });
            },
        });
    }
}
