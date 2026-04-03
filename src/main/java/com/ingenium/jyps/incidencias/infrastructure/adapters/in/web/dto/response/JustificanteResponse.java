package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;

import java.time.LocalDate;
import java.util.List;

public record JustificanteResponse(

         Long id,
         Long empleadoId,
         Long jefeId,
         LocalDate fechaSolicitada,
         LocalDate fechaSolicitud,
         String descripcion,
         List<String>archivos,
         EstadosIncidencia estado
) {
}
