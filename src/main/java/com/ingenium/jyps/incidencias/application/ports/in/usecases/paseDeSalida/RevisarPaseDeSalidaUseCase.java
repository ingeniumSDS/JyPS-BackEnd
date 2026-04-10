package com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.RevisarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

public interface RevisarPaseDeSalidaUseCase {
    PaseDeSalida ejecutar(RevisarPaseDeSalidaCommand command);
}
