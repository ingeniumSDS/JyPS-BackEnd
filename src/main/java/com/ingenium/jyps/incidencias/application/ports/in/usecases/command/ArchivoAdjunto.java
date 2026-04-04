package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;

public record ArchivoAdjunto(
        byte[] contenido,
        String nombreOriginal,
        String contentType
) {}