package com.ingenium.jyps.incidencias.application.services;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import org.springframework.stereotype.Service;

@Service
public class SolicitarJustificanteImpl implements SolicitarJustificanteUseCase {

    @Override
    public Justificante ejecutar(SolicitarJustificanteCommand command) {
        Justificante justificante = new Justificante(
                command.empleadoId(),
                command.jefeId(),
                command.fechaSolicitada(),
                command.descripcion(),
                command.archivos()
        );
        

        return null;
    }
}
