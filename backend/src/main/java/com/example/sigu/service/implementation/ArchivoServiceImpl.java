package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Archivo;
import com.example.sigu.persistence.entity.Materia;
import com.example.sigu.persistence.repository.IArchivoRepository;
import com.example.sigu.persistence.repository.IMateriaRepository;
import com.example.sigu.presentation.dto.archivo.ArchivoRequest;
import com.example.sigu.service.exception.ArchivoNotFoundException;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.interfaces.IArchivoService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.ArchivoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArchivoServiceImpl implements IArchivoService {

    private final IArchivoRepository repository;
    private final IMateriaRepository materiaRepository;
    private final ArchivoMapper archivoMapper;
    private final SecurityUtils securityUtils;
    private final GoogleDriveService googleDriveService;

    @Override
    public Archivo guardarArchivo(MultipartFile file, ArchivoRequest request) throws IOException {
        Archivo uploadedFile = googleDriveService.subirArchivo(file, archivoMapper.toArchivoGoogleDriveRequest(request));
        return repository.save(uploadedFile);
    }

    @Override
    public Optional<Archivo> findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId());
    }

    @Override
    public List<Archivo> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public void eliminarArchivo(Long id) throws IOException {
        Archivo archivoAEliminar = findById(id)
                .orElseThrow(() -> new ArchivoNotFoundException("El archivo con el ID: "+ id+" no existe"));

        if (!archivoAEliminar.getMateria().getSemestre().getUsuario().getId().equals(securityUtils.getCurrentUserId())) {
            throw new AccessDeniedException("Acceso Denegado: No tienes permiso para eliminar la materia con ID: " + id);
        }

        googleDriveService.eliminarArchivo(archivoAEliminar.getGoogleDriveFileId());
        log.info("Archivo {} eliminado correctamente de google Drive.",  archivoAEliminar.getNombre());

        repository.delete(archivoAEliminar);
    }

    @Transactional
    @Override
    public Archivo actualizarArchivo(Long id, ArchivoRequest request, MultipartFile file) throws IOException {
        log.info("Iniciando actualización del archivo con ID: {}", id);

        Archivo archivo = findById(id)
                .orElseThrow(() -> new ArchivoNotFoundException("El archivo con el ID: "+ id+" no existe"));

        archivo.setNombre(request.nombre());
        archivo.setDescripcion(request.descripcion());

        boolean tieneNuevoArchivo = file != null && !file.isEmpty();
        boolean materiaCambio = !archivo.getMateria().getId().equals(request.materiaId());

        if (materiaCambio) {
            Materia materiaNueva = materiaRepository.findById(request.materiaId())
                    .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"));

            log.info("Moviendo archivo de {} a {}", archivo.getMateria().getNombre(), materiaNueva.getNombre());

            String nuevoFolderId = googleDriveService.moverArchivoDeCarpeta(
                    archivo.getGoogleDriveFileId(),
                    materiaNueva.getSemestre().getNombre(),
                    materiaNueva.getNombre()
            );

            archivo.setMateria(materiaNueva);
            archivo.setMateriaFolderId(nuevoFolderId);
        }

        if (tieneNuevoArchivo) {
            log.info("Actualizando archivo en Google Drive");
            googleDriveService.actualizarArchivo(archivo, file);
        }

        return repository.save(archivo);
    }
}
