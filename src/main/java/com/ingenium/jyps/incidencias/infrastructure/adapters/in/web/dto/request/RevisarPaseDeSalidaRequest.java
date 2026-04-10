package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;

public record RevisarPaseDeSalidaRequest(
        long paseDeSalidaId,
        String estado,
        String comentario
) {
}
