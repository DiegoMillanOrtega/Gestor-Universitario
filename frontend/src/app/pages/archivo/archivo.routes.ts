import { Routes } from '@angular/router';

export default [
    {
        path: '',
        loadComponent: () => import('./archivo'),
    },
    { path: 'agregar', loadComponent: () => import('./components/archivo-form') },
    { path: 'editar/:id', loadComponent: () => import('./components/archivo-form') },
] as Routes;
