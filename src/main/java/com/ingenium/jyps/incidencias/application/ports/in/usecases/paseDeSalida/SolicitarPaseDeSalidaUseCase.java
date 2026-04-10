package com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.SolicitarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

public interface SolicitarPaseDeSalidaUseCase {
    PaseDeSalida ejecutar(SolicitarPaseDeSalidaCommand command);
}
