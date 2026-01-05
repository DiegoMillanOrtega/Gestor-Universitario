import { ArchivoResult } from '@/interface/archivo-result';
import { ChangeDetectionStrategy, Component, computed, inject, input, output, signal } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { Table, TableModule } from 'primeng/table';
import { FileIconPipe } from 'src/shared/pipes/file-icon.pipe';
import { FileSizePipe } from 'src/shared/pipes/file-size.pipe';
import { FormsModule } from '@angular/forms';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MateriaService } from '@/pages/service/materia.service';
import { RouterLink } from '@angular/router';

@Component({
    standalone: true,
    imports: [
    TableModule,
    ButtonModule,
    MenuModule,
    FileSizePipe,
    FileIconPipe,
    FormsModule,
    SelectModule,
    DatePickerModule,
    InputTextModule,
    InputIconModule,
    IconFieldModule,
    RouterLink
],
    selector: 'app-archivo-table',
    templateUrl: 'archivo-table.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ArchivoTable {

    //Providers
    private materiaService = inject(MateriaService);

    //Inputs
    archivos = input.required<ArchivoResult[]>();

    //Outputs
    onSee = output<string>();
    onEdit = output<string>();
    onDelete = output<string>();

    //Variables
    archivoId: string = '';
    archivoWebViewLink: string = '';
    searchInputText = signal<string>('');

    //Computed
    optionsMimeType = computed<string[]>(() => {
        const mimeTypes: string[] = this.archivos().map(archivo => archivo.mimeType);
        return [...new Set(mimeTypes)];
    })

    archivosMap = computed<ArchivoResult[]>(() => {
        return [...this.archivos()].map(archivo => {
            return {
                ...archivo,
                fechaModificacion: archivo.fechaModificacion,
                fechaModificacionFormateada: new Date(archivo.fechaModificacion + 'T00:00:00'),
            };
        });
    });

    optionsMaterias = this.materiaService.getAllMateriasBySemestreActivo();

    //MenuItems
    items: MenuItem[] = [
        { label: 'Ver archivo', icon: 'pi pi-fw pi-eye', command: () => this.onSee.emit(this.archivoWebViewLink) },
        { label: 'Editar', icon: 'pi pi-fw pi-pencil', command: () => this.onEdit.emit(this.archivoId) },
        { label: 'Eliminar', icon: 'pi pi-fw pi-trash', command: () => this.onDelete.emit(this.archivoId) },
    ];

    fileColors: { [key: string]: string } = {
        pdf: '#f44336', // Rojo
        word: '#2196f3', // Azul
        excel: '#4caf50', // Verde
        image: '#9c27b0', // Morado
        folder: '#ffb300', // Amarillo/Naranja
        powerpoint: '#ff5722', // Naranja fuerte
        default: '#607d8b', // Gris
    };

    clear(table: Table) {
        table.clear();
        this.searchInputText.set('');
    }

    getFileColor(mimeType: string): string {
        if (mimeType.includes('pdf')) return this.fileColors['pdf'];
        if (mimeType.includes('word') || mimeType.includes('document'))
            return this.fileColors['word'];
        if (mimeType.includes('excel') || mimeType.includes('spreadsheet'))
            return this.fileColors['excel'];
        if (mimeType.includes('image')) return this.fileColors['image'];
        if (mimeType.includes('folder')) return this.fileColors['folder'];
        if (
            mimeType.includes('presentation') ||
            mimeType.includes('powerpoint')
        )
            return this.fileColors['powerpoint'];
        return this.fileColors['default'];
    }

    filterDate(date: Date, filterCallBack: Function) {
        console.log(date);
        
        const formattedDate = date.toISOString().split('T')[0];
        return filterCallBack(formattedDate);
    }

}
