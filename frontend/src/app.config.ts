import {
    provideHttpClient,
    withFetch,
    withInterceptors,
} from '@angular/common/http';
import { ApplicationConfig, LOCALE_ID } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {
    provideRouter,
    withComponentInputBinding,
    withEnabledBlockingInitialNavigation,
    withInMemoryScrolling,
} from '@angular/router';
import Aura from '@primeuix/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';

registerLocaleData(localeEs)

export const appConfig: ApplicationConfig = {
    providers: [
        { provide: LOCALE_ID, useValue: 'es-ES' },
        provideRouter(
            appRoutes,
            withInMemoryScrolling({
                anchorScrolling: 'enabled',
                scrollPositionRestoration: 'enabled',
            }),
            withEnabledBlockingInitialNavigation(),
            withComponentInputBinding(),
        ),
        provideHttpClient(withFetch(), withInterceptors([authInterceptor])),
        provideAnimationsAsync(),
        providePrimeNG({
            theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } },
            translation: {
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
                dayNamesShort: [
                    'Dom',
                    'Lun',
                    'Mar',
                    'Mié',
                    'Jue',
                    'Vir',
                    'Sáb',
                ],
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
                dateFormat: 'dd/mm/yy',
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
            },
        }),
        
    ],
};
