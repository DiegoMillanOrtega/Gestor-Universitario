import { Routes } from "@angular/router";


export default [
    { path: '', loadComponent: () => import('./materia') },
    { path: 'agregar', loadComponent: () => import('./components/materia-form') },
    { path: 'editar/:id', loadComponent: () => import('./components/materia-form') },
] as Routes;