package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

public record CrearDepartamentoRequest(
        String nombre,
        String descripcion,
        Long jefeId
) {
}
