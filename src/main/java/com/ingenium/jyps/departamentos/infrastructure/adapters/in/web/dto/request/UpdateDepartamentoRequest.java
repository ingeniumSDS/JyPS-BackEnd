package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateDepartamentoRequest(

        @NotNull(message = "El id del departamento es obligatorio")
        @NotBlank(message = "El id del departamento no puede estar vacío")
        @Positive(message = "El id del departamento debe ser un número positivo")
        Long id,

        @NotBlank(message = "El nombre del departamento es obligatorio")
        @NotNull(message = "El nombre del departamento no puede ser nulo")
        String nombre,

        @NotNull(message = "La descripción del departamento no puede ser nula.")
        String descripcion,

        Long jefeId
) {
}
