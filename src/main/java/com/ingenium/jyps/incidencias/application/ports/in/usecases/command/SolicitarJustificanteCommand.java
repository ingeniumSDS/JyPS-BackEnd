package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;


import java.time.LocalDate;

public record SolicitarJustificanteCommand(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        String descripcion
) {

}
