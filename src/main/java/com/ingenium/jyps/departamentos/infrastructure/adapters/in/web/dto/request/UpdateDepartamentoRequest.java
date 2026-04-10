package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

public record UpdateDepartamentoRequest(
        Long id,
        String nombre,
        String descripcion,
        Long jefeId
) {
}
