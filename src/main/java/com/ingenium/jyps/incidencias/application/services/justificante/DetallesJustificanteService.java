package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.DetallesJustificanteUseCase;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetallesJustificanteService implements DetallesJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public Justificante ejecutar(Long id) {
        return justificanteRepositoryPort.buscarPorId(id);
    }
}
