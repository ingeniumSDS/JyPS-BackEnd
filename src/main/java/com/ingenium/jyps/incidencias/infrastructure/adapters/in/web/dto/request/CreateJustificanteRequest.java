package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;

import jakarta.mail.Multipart;

import java.time.LocalDate;

public record CreateJustificanteRequest(
        Long idEmpleado,
        Long idJefe,
        LocalDate fechaSolicitada,
        String descripcion,
        Multipart archivos
) {
}
