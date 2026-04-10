package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PaseDeSalidaResponse(
        Long id,
        Long empleadoId,
        String nombreCompleto,
        Long jefeId,
        LocalDateTime horaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        List<String> archivos,
        String comentario,
        String QR,
        String estado,
        LocalDateTime horaSalidaReal,
        LocalDateTime horaEsperada
        ) {
}
