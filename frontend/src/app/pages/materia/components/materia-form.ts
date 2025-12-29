import {
    MateriaInterface,
    MateriaRequestInterface,
} from '@/interface/materia.interface';
import { httpResource } from '@angular/common/http';
import { Component, inject, signal, effect, computed } from '@angular/core';
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
export class MateriaForm {
    //Providers
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private formBuilder = inject(FormBuilder);
    private messageService = inject(MessageService);

    //Services
    private materiaService = inject(MateriaService);

    //Variables
    materiaId = signal(this.route.snapshot.paramMap.get('id'));
    formEnviado = signal(false);

    // --- Computed UI ---
    esEdicion = computed(() => !!this.materiaId());
    headerText = computed(() =>
        this.esEdicion() ? 'Editar materia' : 'Agregar materia',
    );
    labelButton = computed(() =>
        this.esEdicion() ? 'Editar materia' : 'Crear materia',
    );

    // HttpResources
    semestreResource = httpResource<SemestreInterface[]>(() => ({
        url: `http://localhost:8080/api/semestres`,
    }));

    materiaResource = httpResource<MateriaInterface>(() => {
        const id = this.materiaId();
        return id
            ? { url: `http://localhost:8080/api/materias/${id}` }
            : undefined;
    });

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

            if (materia) {
                this.formMateria.patchValue({
                    ...materia,
                    semestreId: materia.semestre.id,
                });
            }
        });

        effect(() => {
            const semestres = this.semestreResource.value();
            if (semestres && !this.esEdicion()) {
                const activo = semestres.find((s) => s.estado === 'ACTIVO');
                if (activo)
                    this.formMateria.controls.semestreId.setValue(activo.id);
            }
        });
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
