package com.ingenium.jyps.incidencias.application.services;

import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitarJustificanteService implements SolicitarJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public Justificante ejecutar(SolicitarJustificanteCommand command) {

        Justificante nuevoJustificante = new Justificante(
                command.empleadoId(),
                command.jefeId(),
                command.fechaSolicitada(),
                command.descripcion(),
                command.archivos()
        );

        return  justificanteRepositoryPort.solicitar(nuevoJustificante);

    }
}
