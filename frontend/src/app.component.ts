import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule, ToastModule, ConfirmDialogModule],
    providers: [MessageService, ConfirmationService],
    template: `
    <p-toast></p-toast>
    <p-confirmdialog key="globalConfirm" [focusTrap]="false" appendTo="body"></p-confirmdialog>
    <router-outlet></router-outlet>
    `
})
export class AppComponent {}
