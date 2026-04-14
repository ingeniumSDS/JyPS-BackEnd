package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.JustificantesPorJefeUseCase;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JustificantesPorJefeService implements JustificantesPorJefeUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public List<Justificante> ejecutar(Long usuarioId) {
        return justificanteRepositoryPort.buscarPorJefe(usuarioId);
    }
}
