package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;

public record RevisarJustificanteRequest(
        long justificanteId,
        String estado,
        String comentario
) {
}
