import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { IftaLabelModule } from 'primeng/iftalabel';
import { DividerModule } from 'primeng/divider';
import { CargaAcademica, SemestreInterface, SemestreStatus } from '@/interface/semestre.interface';
import { ButtonModule } from "primeng/button";
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SemestreService } from '@/pages/service/semestre.service';
import { formatFormDates } from '@/utils/date-formatter.util';
import { AuthService } from 'src/core/services/auth.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';

@Component({
    selector: 'app-add-semestre-form',
    imports: [CardModule, InputTextModule, ReactiveFormsModule, SelectModule, DatePickerModule, SelectModule, IftaLabelModule, DividerModule, ButtonModule, RouterLink, ToastModule, MessageModule],
    providers: [MessageService],
    templateUrl: 'add-semestre-form.html'
})
export class AddSemestreForm implements OnInit {
    private formBuilder = inject(FormBuilder);
    private route = inject(ActivatedRoute);
    private semestreService = inject(SemestreService);
    private router = inject(Router);
    private authService = inject(AuthService);
    private messageService = inject(MessageService);

    semestreId: string | null = null;
    header: string = 'Agregar nuevo Semestre';
    labelButtonSave: string = 'Crear semestre';
    formEnviado: boolean = false;

    estadoOptions: { label: string; value: SemestreStatus }[] = [
        { label: 'Activo', value: 'ACTIVO' },
        { label: 'Finalizado', value: 'FINALIZADO' },
        { label: 'Planificado', value: 'PLANIFICADO' }
    ];

    // cargaAcademicaOptions: { label: string; value: CargaAcademica }[] = [
    //     { label: 'Carga baja', value: 'BAJA' },
    //     { label: 'Carga media', value: 'MEDIA' },
    //     { label: 'Carga alta', value: 'ALTA' }
    // ];

    estadoStyleClass: Record<SemestreStatus, string> = {
        ACTIVO: '!text-green-500',
        FINALIZADO: '!text-gray-500',
        PLANIFICADO: '!text-yellow-500'
    };

    // cargaAcademicaStyleClass: Record<CargaAcademica, string> = {
    //     BAJA: '!text-green-500',
    //     MEDIA: '!text-yellow-500',
    //     ALTA: '!text-red-500'
    // };

    semestreForm = this.formBuilder.group({
        id: [null as string | null],
        nombre: ['', Validators.required],
        cargaAcademica: [''],
        fechaInicio: [null as Date | null, Validators.required],
        fechaFin: [null as Date | null, Validators.required],
        estado: ['', Validators.required]
    });

    ngOnInit() {
        this.semestreId = this.route.snapshot.paramMap.get('id');
        if (this.semestreId) {
            this.obtenerSemestrePorId(this.semestreId);
            this.header = 'Editar Semestre';
            this.labelButtonSave = 'Editar semestre';
        }
    }

    getEstadoClass(valor: any): string {
        const key = valor as SemestreStatus;
        return this.estadoStyleClass[key] || '';
    }

    // getCargaAcademicaClass(valor: any): string {
    //     const key = valor as CargaAcademica;
    //     return this.cargaAcademicaStyleClass[key] || '';
    // }

    esInvalido(controlName: string) {
        const control = this.semestreForm.get(controlName);
        return control?.invalid && (control.touched || this.formEnviado)
    }

    obtenerSemestrePorId(id: string) {
        this.semestreService.obtenerSemestrePorId(id).subscribe({
            next: (semestre) => {
                this.setSemestreForm(semestre);
            },
            error: (err) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: err.error.message, life: 5000 });
            }
        });
    }

    setSemestreForm(semestre: SemestreInterface) {
        console.log(semestre);
        
        this.semestreForm.patchValue({
            ...semestre,
            fechaInicio: semestre.fechaInicio ? new Date(semestre.fechaInicio + 'T00:00:00') : null,
            fechaFin: semestre.fechaFin ? new Date(semestre.fechaFin + 'T00:00:00') : null
        });
    }

    onSubmit() {
        this.formEnviado = true;
        console.log(this.semestreForm.invalid);
        
        if (this.semestreForm.invalid) return;

        const payload: SemestreInterface = formatFormDates(this.semestreForm.value);

        const userId = this.authService.getUserId();
        
        if (userId) {
            payload.usuarioId = userId;
        }

        this.semestreService.guardaSemestre(formatFormDates(payload)).subscribe((semestre) => {
            this.formEnviado = false;
            this.router.navigate(['/pages/semestres']);
        },
        err => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: err.error.message, life: 5000 });
        }
    );
    }
}
