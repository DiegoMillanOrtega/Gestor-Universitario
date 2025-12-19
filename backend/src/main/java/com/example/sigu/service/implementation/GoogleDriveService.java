package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.repository.IArchivoRepository;
import com.example.sigu.presentation.dto.archivo.ArchivoGoogleDriveRequest;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleDriveService {

    private final Drive driveService;
    private final IArchivoRepository archivoRepository;
    private final IMateriaService materiaService;

    @Value("${google.drive.root-folder-id}")
    private String siguFolderId;

    private final Map<String, String> folderCache = new HashMap<>();

    @Transactional
    public Archivo subirArchivo(MultipartFile file, ArchivoGoogleDriveRequest request) throws IOException {
        String semestre = request.materia().getSemestre().getNombre();
        String materia = request.materia().getNombre();

        log.info("Iniciando subida de archivo: {} para semestre: {}, materia: {}",
                file.getOriginalFilename(), semestre, materia);


        String semestreFolderId = obtenerOCrearCarpeta(semestre, siguFolderId);
        String materiaFolderId = obtenerOCrearCarpeta(materia, semestreFolderId);

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(materiaFolderId));

        InputStreamContent mediaContent = new InputStreamContent(
                file.getContentType(),
                file.getInputStream()
        );

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, name, mimeType, size, webViewLink, createdTime")
                .execute();

        log.info("Archivo subido exitosamente con ID: {}", uploadedFile.getId());

        return Archivo.builder()
                .googleDriveFileId(uploadedFile.getId())
                .nombre(uploadedFile.getName())
                .mimeType(uploadedFile.getMimeType())
                .tamano(uploadedFile.getSize())
                .materiaFolderId(materiaFolderId)
                .semestreFolderId(semestreFolderId)
                .googleDriveFileId(uploadedFile.getId())
                .googleDriveWebViewLink(uploadedFile.getWebViewLink())
                .descripcion(request.descripcion())
                .materia(request.materia())
                .semestreFolderId(semestreFolderId)
                .build();
    }

    @Transactional
    public void eliminarArchivo(String googleDriveFileId) throws IOException {
        log.info("Eliminando archivo con ID: {}", googleDriveFileId);

        driveService.files().delete(googleDriveFileId).execute();

        archivoRepository.findByGoogleDriveFileId(googleDriveFileId)
                .ifPresent(archivoRepository::delete);

        log.info("Archivo eliminado con ID: {}", googleDriveFileId);
    }

    @Transactional
    public void actualizarArchivo(Archivo archivoEntidad, MultipartFile newFile) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(newFile.getOriginalFilename());

        InputStreamContent mediaContent = new InputStreamContent(
                newFile.getContentType(),
                newFile.getInputStream()
        );

        File updatedFile = driveService.files().update(archivoEntidad.getGoogleDriveFileId(), fileMetadata, mediaContent)
                .setFields("id, mimeType, size, webViewLink")
                .execute();

        log.info("Contenido del archivo actualizado exitosamente");

        archivoEntidad.setTamano(updatedFile.getSize());
        archivoEntidad.setMimeType(updatedFile.getMimeType());
        archivoEntidad.setGoogleDriveWebViewLink(updatedFile.getWebViewLink());
    }

    @Transactional
    public String moverArchivoDeCarpeta(String googleDriveFileId, String semestre, String materia) throws IOException {
        String materiaFolderId =  obtenerOCrearCarpeta(materia, obtenerOCrearCarpeta(semestre, siguFolderId));

        File file = driveService.files().get(googleDriveFileId).setFields("parents").execute();
        String previousParents = String.join(",", file.getParents());

        driveService.files().update(googleDriveFileId, null)
                .setAddParents(materiaFolderId)
                .setRemoveParents(previousParents)
                .execute();

        log.info("Archivo movido a nueva ubicacion. Nueva carpeta ID: {}", materiaFolderId);
        return materiaFolderId;
    }

    private String obtenerOCrearCarpeta(String folderName, String parentId) throws IOException {
        String cacheKey = parentId != null ? parentId + "/" + folderName : folderName;

        // Verificar cache
        if (folderCache.containsKey(cacheKey)) {
            return folderCache.get(cacheKey);
        }

        // Buscar carpeta existente
        String query = "mimeType='application/vnd.google-apps.folder' " +
                "and name='" + folderName + "' " +
                "and trashed=false";

        if (parentId != null) {
            query += " and '" + parentId + "' in parents";
        }

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute();

        List<File> files = result.getFiles();

        if (files != null && !files.isEmpty()) {
            String folderId = files.getFirst().getId();
            folderCache.put(cacheKey, folderId);
            log.info("Carpeta encontrada: {} con ID: {}", folderName, folderId);
            return folderId;
        }

        // Crear nueva carpeta
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        if (parentId != null) {
            fileMetadata.setParents(Collections.singletonList(parentId));
        }

        File folder = driveService.files().create(fileMetadata)
                .setFields("id, name")
                .execute();

        folderCache.put(cacheKey, folder.getId());
        log.info("Carpeta creada: {} con ID: {}", folderName, folder.getId());

        return folder.getId();
    }
}
