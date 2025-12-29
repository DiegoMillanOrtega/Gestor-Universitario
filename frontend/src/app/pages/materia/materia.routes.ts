import { Routes } from "@angular/router";
import { Materia } from "./materia";
import { MateriaForm } from "./components/materia-form";

export default [
    { path: '', component: Materia },
    { path: 'agregar', component:  MateriaForm },
    { path: 'editar/:id', component:  MateriaForm },
] as Routes;