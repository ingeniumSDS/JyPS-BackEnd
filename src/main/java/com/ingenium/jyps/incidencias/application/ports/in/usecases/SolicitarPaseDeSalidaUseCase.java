package com.ingenium.jyps.incidencias.application.ports.in.usecases;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

public interface SolicitarPaseDeSalidaUseCase {
    PaseDeSalida ejecutar(SolicitarPaseDeSalidaCommand command);
}
