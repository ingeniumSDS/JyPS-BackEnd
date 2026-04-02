package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;

import java.time.LocalDate;
import java.util.List;

public record SolicitarJustificanteCommand(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        List<String> archivos,
        EstadosIncidencia estado
) {

}
