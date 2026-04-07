package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;
import java.time.LocalDate;
import java.util.List;

public record SolicitarJustificanteCommand(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        List<ArchivoAdjunto> archivos
) {

}
