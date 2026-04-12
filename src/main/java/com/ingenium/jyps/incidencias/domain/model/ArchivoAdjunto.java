package com.ingenium.jyps.incidencias.domain.model;


public record ArchivoAdjunto(
        String nombreOriginal,
        byte[] contenido
) {
}
