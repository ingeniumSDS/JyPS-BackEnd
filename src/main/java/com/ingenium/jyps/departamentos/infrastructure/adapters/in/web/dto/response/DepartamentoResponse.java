package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

import java.util.Optional;

public record DepartamentoResponse(
        Long id,
        String nombre,
        String descripcion,
        Optional<Long> jefeId,
        String nombreJefe,
        boolean activo,
        Long totalEmpleados
) {

    public static DepartamentoResponse desdeDominio(Departamento departamento, String nombreJefe, Long totalEmpleados) {

        return new DepartamentoResponse(
                departamento.getId(),
                departamento.getNombre(),
                departamento.getDescripcion(),
                Optional.ofNullable(departamento.getJefeId()),
                nombreJefe,
                departamento.isActivo(),
                totalEmpleados

        );
    }
}
