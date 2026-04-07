package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;


import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record SolicitarJustificanteRequest(
        Long empleadoId,
        Long jefeId,
        LocalDate fechaSolicitada,
        LocalDate fechaSolicitud,
        String descripcion,
        List<MultipartFile> archivos
) {
}
