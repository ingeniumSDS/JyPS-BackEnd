package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;

import java.time.LocalDate;
import java.util.List;

public record JustificanteRequest(
        Long idEmpleado,
        Long idJefe,
        LocalDate fechaSolicitada,
        String descripcion,
        List<String> archivos
) {
}
