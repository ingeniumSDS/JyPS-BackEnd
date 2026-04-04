package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;


import java.time.LocalDate;

public record JustificanteRequest(
        Long idEmpleado,
        Long idJefe,
        LocalDate fechaSolicitada,
        String descripcion
) {
}
