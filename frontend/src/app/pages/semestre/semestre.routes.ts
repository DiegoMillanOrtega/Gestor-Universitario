import { Routes } from "@angular/router";

export default [
    { path: '', loadComponent: () => import('./semestre') },
    { path: 'agregar', loadComponent: () => import('./components/add-semestre-form') },
    { path: 'editar/:id', loadComponent: () => import('./components/add-semestre-form') },
] as Routes;