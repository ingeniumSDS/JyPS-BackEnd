package com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;

public record RevisarPaseDeSalidaCommand(
        long paseDeSalidaId,
        EstadosIncidencia estado,
        String comentario
) {
}
