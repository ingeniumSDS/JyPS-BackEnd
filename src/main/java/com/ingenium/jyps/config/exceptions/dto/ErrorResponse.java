package com.ingenium.jyps.config.exceptions.dto;

import java.util.Map;

public record ErrorResponse(
        String mensaje,
        int codigo,
        long timestamp,
        Map<String, String> detalles // Aquí irán los errores de @Valid
) {
}