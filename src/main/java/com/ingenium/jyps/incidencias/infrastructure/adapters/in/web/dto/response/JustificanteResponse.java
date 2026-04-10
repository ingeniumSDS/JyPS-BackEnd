package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response;

import java.time.LocalDate;
import java.util.List;

public record JustificanteResponse(
        Long id,
        Long empleadoId,
        String nombreCompleto,
        Long jefeId,
        LocalDate fechaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        String estado,
        String comentario,
        List<String> archivos
) {
}
