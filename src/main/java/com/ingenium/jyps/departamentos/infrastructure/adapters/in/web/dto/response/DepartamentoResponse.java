package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;

import java.util.List;

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
