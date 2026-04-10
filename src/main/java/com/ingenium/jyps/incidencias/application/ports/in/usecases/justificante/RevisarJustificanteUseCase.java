package com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;

public interface RevisarJustificanteUseCase {
    Justificante ejecutar(RevisarJustificanteCommand command);
}
