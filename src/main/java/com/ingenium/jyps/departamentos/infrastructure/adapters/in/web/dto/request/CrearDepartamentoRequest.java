package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

import java.util.Optional;

public record CrearDepartamentoRequest(
        String nombre,
        String descripcion,
        Optional<Long> jefeId
) {
}
