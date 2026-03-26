package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

public record DepartamentoResponse(
        Long id,
        String nombre,
        String descripcion,
        Long jefeId
) {

    public static DepartamentoResponse desdeDominio(Departamento departamento) {

        return new DepartamentoResponse(
               departamento.getId(),
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.getJefeId()
        );
    }
}
