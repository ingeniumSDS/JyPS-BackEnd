package com.ingenium.jyps.incidencias.application.services;

import com.ingenium.jyps.incidencias.application.ports.out.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import org.springframework.stereotype.Service;

@Service
public class SolicitarJustificanteImpl implements SolicitarJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    public SolicitarJustificanteImpl(JustificanteRepositoryPort justificanteRepositoryPort) {
        this.justificanteRepositoryPort = justificanteRepositoryPort;
    }

    @Override
    public Justificante ejecutar(SolicitarJustificanteCommand command) {

        if (justificanteRepositoryPort.existePorFechaSolicitada(command.fechaSolicitada(), command.empleadoId())) {
            throw new RuntimeException("Ya existe un justificante para la fecha solicitada: " + command.fechaSolicitada());
        }

        Justificante justificante = new Justificante(
                command.empleadoId(),
                command.jefeId(),
                command.fechaSolicitada(),
                command.descripcion(),
                command.archivos()
        );


        return justificanteRepositoryPort.solicitar(justificante);
    }
}
