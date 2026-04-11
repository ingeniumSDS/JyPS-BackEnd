package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.PastOrPresent;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record RangoDeFechasRequest
        (
                @PastOrPresent(message = "La fecha de inicio no puede ser futura")
                LocalDate fechaInicio,
                @PastOrPresent(message = "La fecha de fin no puede ser futura")
                LocalDate fechaFin
        ) {

    public RangoDeFechasRequest {
        // 1. Asignar valores por defecto
        if (fechaInicio == null) fechaInicio = LocalDate.now().minusDays(3);
        if (fechaFin == null) fechaFin = LocalDate.now();

        // 2. Validación de consistencia (Business Rule)
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
    }

}
