package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;



import java.time.LocalDate;

public record SolicitarJustificanteRequest(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        String descripcion
) {
}
