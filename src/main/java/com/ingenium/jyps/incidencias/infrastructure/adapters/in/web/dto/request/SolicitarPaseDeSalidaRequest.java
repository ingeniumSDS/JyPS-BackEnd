package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;


import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record SolicitarPaseDeSalidaRequest(
        // Dominio de la incidencia
        Long id,
        Long empleadoId,
        Long jefeId,
        String descripcion,
        String motivoRechazo,

        // Extras para el dominio del pase de salida
        LocalDateTime horaSolicitada,
        List<MultipartFile> archivos
) {
}
