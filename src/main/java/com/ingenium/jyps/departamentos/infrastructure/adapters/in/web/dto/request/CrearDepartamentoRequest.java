package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearDepartamentoRequest(
        @NotNull(message = "El nombre del departamento no puede ser nulo")
        @NotBlank(message = "El nombre del departamento no puede estar vacío")
        String nombre,

        String descripcion,

        Long jefeId
) {
}
