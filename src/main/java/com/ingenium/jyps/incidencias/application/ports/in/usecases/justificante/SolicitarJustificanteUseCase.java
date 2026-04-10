package com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;


public interface SolicitarJustificanteUseCase {
    Justificante ejecutar(SolicitarJustificanteCommand command);
}
