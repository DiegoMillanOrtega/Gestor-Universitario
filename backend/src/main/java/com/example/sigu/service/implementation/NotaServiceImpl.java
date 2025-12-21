package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Nota;
import com.example.sigu.persistence.repository.INotaRepository;
import com.example.sigu.presentation.dto.nota.NotaRequest;
import com.example.sigu.presentation.dto.nota.NotaUpdateRequest;
import com.example.sigu.service.exception.MateriaNotFoundException;
import com.example.sigu.service.exception.NotaCalcularRequerimientoException;
import com.example.sigu.service.exception.NotaNotFoundException;
import com.example.sigu.service.interfaces.IMateriaService;
import com.example.sigu.service.interfaces.INotaService;
import com.example.sigu.util.SecurityUtils;
import com.example.sigu.util.mapper.NotaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements INotaService {

    private static final BigDecimal NOTA_MINIMA_APROBAR = new BigDecimal("3.0");
    private static final BigDecimal PESO_PARCIALES = new BigDecimal("0.7");
    private static final BigDecimal PESO_EXAMEN = new BigDecimal("0.3");

    private final INotaRepository repository;
    private final IMateriaService materiaService;
    private final SecurityUtils securityUtils;
    private final NotaMapper mapper;

    @Override
    public List<Nota> findAll() {
        return repository.findAllByMateria_Semestre_UsuarioId(securityUtils.getCurrentUserId());
    }

    @Override
    public Nota findById(Long id) {
        return repository.findByIdAndMateria_Semestre_UsuarioId(id, securityUtils.getCurrentUserId())
                .orElseThrow(() -> new NotaNotFoundException(id));
    }

    @Override
    public Nota save(NotaRequest request) {
        Nota nota = mapper.toEntity(request);
        nota.setMateria(materiaService.findById(request.materiaId()));

        return repository.save(nota);
    }

    @Override
    public Nota update(Long notaId, NotaUpdateRequest request) {
        Nota nota = findById(notaId);
        if (request.materiaId() != null &&  !request.materiaId().equals(nota.getMateria().getId())) {
            nota.setMateria(materiaService.findById(request.materiaId()));
        }

        mapper.toEntityUpdateDto(request, nota);
        return repository.save(nota);
    }

    @Override
    public void delete(Long id) {
        Nota notaToDelete = findById(id);
        repository.delete(notaToDelete);
    }

    @Override
    public Map<String, BigDecimal> calcularRequerimiento(Long notaId) {
        Nota nota = findById(notaId);

        if (nota.getP1().compareTo(BigDecimal.ZERO) == 0)
            throw new NotaCalcularRequerimientoException("Primer Parcial (P1)");

        Map<String, BigDecimal> proyeccion = new HashMap<>();

        // 1. Identificar qué notas faltan (asumiendo 0.0 como no ingresada)
        boolean faltaP2 = nota.getP2().compareTo(BigDecimal.ZERO) == 0;
        boolean faltaP3 = nota.getP3().compareTo(BigDecimal.ZERO) == 0;
        boolean faltaEx = nota.getEx().compareTo(BigDecimal.ZERO) == 0;

        // 2. Calcular cuánto puntaje total (sobre 5.0) ya lleva ganado
        // Cada parcial sobre el total vale: (1/3) * 0.7 = 0.2333...
        BigDecimal pesoCadaParcial = PESO_PARCIALES.divide(new BigDecimal("3"), 4, RoundingMode.HALF_UP);

        BigDecimal acumulado = nota.getP1().multiply(pesoCadaParcial);
        if (!faltaP2) acumulado = acumulado.add(nota.getP2().multiply(pesoCadaParcial));
        if (!faltaP3) acumulado = acumulado.add(nota.getP3().multiply(pesoCadaParcial));
        if (!faltaEx) acumulado = acumulado.add(nota.getEx().multiply(PESO_EXAMEN));

        BigDecimal puntosFaltantes = NOTA_MINIMA_APROBAR.subtract(acumulado);

        // 3. Lógica de proyección (Ejemplo: Si falta P2, P3 y EX)
        if (faltaP2 && faltaP3 && faltaEx) {
            // Suponiendo que saca la misma nota en todo lo que falta
            // Ecuación: x*(pesoCadaParcial * 2) + x*0.3 = puntosFaltantes
            BigDecimal pesoRestanteTotal = pesoCadaParcial.multiply(new BigDecimal("2")).add(PESO_EXAMEN);
            BigDecimal notaNecesaria = puntosFaltantes.divide(pesoRestanteTotal, 2, RoundingMode.HALF_UP);
            proyeccion.put("nota_necesaria_en_todo_lo_faltante", notaNecesaria);
        }
        else if (faltaEx) {
            // Si solo falta el examen final
            BigDecimal notaNecesariaEx = puntosFaltantes.divide(PESO_EXAMEN, 2, RoundingMode.HALF_UP);
            proyeccion.put("necesitas_en_examen_final", notaNecesariaEx);
        }

        return proyeccion;
    }
}
