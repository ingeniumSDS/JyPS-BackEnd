package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;


public record ArchivoAdjunto(
        String nombreOriginal,
        String tipoContenido,
        byte[] contenido
) {
}
