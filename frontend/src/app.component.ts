import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { PrimeNG } from 'primeng/config';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule, ToastModule, ConfirmDialogModule],
    providers: [MessageService, ConfirmationService],
    template: `
        <p-toast></p-toast>
        <p-confirmdialog
            key="globalConfirm"
            [focusTrap]="false"
            appendTo="body"
        ></p-confirmdialog>
        <router-outlet></router-outlet>
    `,
})
export class AppComponent implements OnInit {
    private config = inject(PrimeNG);

    ngOnInit() {
        this.config.setTranslation({
            accept: 'Aceptar',
            reject: 'Rechazar',
            choose: 'Elegir',
            upload: 'Subir',
            cancel: 'Cancelar',
            dayNames: [
                'Domingo',
                'Lunes',
                'Martes',
                'Miércoles',
                'Jueves',
                'Viernes',
                'Sábado',
            ],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vir', 'Sáb'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
            monthNames: [
                'Enero',
                'Febrero',
                'Marzo',
                'Abril',
                'Mayo',
                'Junio',
                'Julio',
                'Agosto',
                'Septiembre',
                'Octubre',
                'Noviembre',
                'Diciembre',
            ],
            monthNamesShort: [
                'Ene',
                'Feb',
                'Mar',
                'Abr',
                'May',
                'Jun',
                'Jul',
                'Ago',
                'Sep',
                'Oct',
                'Nov',
                'Dic',
            ],
            today: 'Hoy',
            weekHeader: 'Sem',
            firstDayOfWeek: 0,
            dateFormat: 'yy-mm-dd',
            dateIs: 'Fecha es',
            dateIsNot: 'Fecha no es',
            dateBefore: 'Fecha antes de',
            dateAfter: 'Fecha después de',
            weak: 'Débil',
            medium: 'Medio',
            strong: 'Fuerte',
            passwordPrompt: 'Ingrese una contraseña',
            emptyMessage: 'No se encontraron resultados',
            emptyFilterMessage: 'No se encontraron resultados',
            startsWith: 'Comienza con',
            contains: 'Contiene',
            notContains: 'No contiene',
            endsWith: 'Termina con',
            equals: 'Igual a',
            notEquals: 'No igual a',
            noFilter: 'Sin filtro',
            lt: 'Menor que',
            lte: 'Menor o igual que',
            gt: 'Mayor que',
            gte: 'Mayor o igual que',
            is: 'Es',
            isNot: 'No es',
            before: 'Antes',
            after: 'Después',
            clear: 'Limpiar',
            apply: 'Aplicar',
            matchAll: 'Coincidir con todos',
            matchAny: 'Coincidir con alguno',
            addRule: 'Agregar regla',
            removeRule: 'Eliminar regla',
        });
    }
}
