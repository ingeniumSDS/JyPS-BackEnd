package com.ingenium.jyps.incidencias.application.ports.in.usecases;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;

public interface SolicitarJustificanteUseCase {
    Justificante ejecutar(SolicitarJustificanteCommand command);
}
