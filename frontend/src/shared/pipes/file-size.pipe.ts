import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileSize',
  standalone: true // Si usas Standalone Components
})
export class FileSizePipe implements PipeTransform {

  private units = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];

  transform(bytes: number | string, precision: number = 2): string {
    if (isNaN(parseFloat(String(bytes))) || !isFinite(Number(bytes)) || Number(bytes) <= 0) {
      return '0 Bytes';
    }

    let unitIndex = 0;
    let size = Number(bytes);

    while (size >= 1024 && unitIndex < this.units.length - 1) {
      size /= 1024;
      unitIndex++;
    }

    return `${size.toFixed(precision)} ${this.units[unitIndex]}`;
  }
}