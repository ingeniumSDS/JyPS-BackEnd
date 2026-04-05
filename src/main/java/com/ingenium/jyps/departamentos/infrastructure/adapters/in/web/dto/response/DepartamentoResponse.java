package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.users.domain.model.Usuario;

public record DepartamentoResponse(
        Long id,
        String nombre,
        String descripcion,
        Long jefeId,
        String nombreJefe,
        boolean activo,
        Long totalEmpleados
) {

    public static DepartamentoResponse desdeDominio(Departamento departamento, Usuario jefe, Long totalEmpleados) {
        return new DepartamentoResponse(
                departamento.getId(),
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.getJefeId(),
                jefe.nombreCompleto(),
                departamento.isActivo(),
                totalEmpleados

        );
    }
}
