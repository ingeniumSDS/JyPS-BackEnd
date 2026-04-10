package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.RevisarJustificanteUseCase;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisarJustificanteService implements RevisarJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public Justificante ejecutar(RevisarJustificanteCommand command) {
        return justificanteRepositoryPort.buscarPorId(command.justificanteId());

    }
}
