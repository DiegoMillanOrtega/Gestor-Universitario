import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileIcon',
  standalone: true
})
export class FileIconPipe implements PipeTransform {
  transform(mimeType: string): string {
    if (!mimeType) return 'pi pi-file';

    if (mimeType.startsWith('image/')) return 'pi pi-image';
    if (mimeType.startsWith('video/')) return 'pi pi-video';
    if (mimeType.startsWith('audio/')) return 'pi pi-volume-up';

    const icons: { [key: string]: string } = {
      'application/pdf': 'pi pi-file-pdf',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'pi pi-file-word',
      'application/msword': 'pi pi-file-word',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': 'pi pi-file-excel',
      'application/vnd.ms-excel': 'pi pi-file-excel',
      'application/vnd.openxmlformats-officedocument.presentationml.presentation': 'pi pi-file-powerpoint',
      'application/zip': 'pi pi-file-archive',
      'application/vnd.google-apps.folder': 'pi pi-folder-open',
      'application/vnd.google-apps.document': 'pi pi-file-word',
      'application/vnd.google-apps.spreadsheet': 'pi pi-file-excel',
      'text/plain': 'pi pi-file-edit'
    };

    return icons[mimeType] || 'pi pi-file';
  }
}