package com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;

public record RevisarJustificanteCommand(
        long justificanteId,
        EstadosIncidencia estado,
        String comentario
) {
}
