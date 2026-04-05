package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response;

import java.time.LocalDate;
import java.util.List;

public record JustificanteResponse(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        List<String> archivos
) {
}
