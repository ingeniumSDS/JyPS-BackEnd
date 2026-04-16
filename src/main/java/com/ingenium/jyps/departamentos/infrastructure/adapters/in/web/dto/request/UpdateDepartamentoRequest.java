package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateDepartamentoRequest(

        Long id,

        @NotBlank(message = "El nombre del departamento es obligatorio")
        @NotNull(message = "El nombre del departamento no puede ser nulo")
        String nombre,

        String descripcion,

        Long jefeId
) {
}
