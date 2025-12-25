import { Routes } from "@angular/router";
import { Semestre } from "./semestre";
import { AddSemestreForm } from "./components/add-semestre-form";

export default [
    { path: '', component: Semestre },
    { path: 'agregar', component: AddSemestreForm },
    { path: 'editar/:id', component: AddSemestreForm },
] as Routes;