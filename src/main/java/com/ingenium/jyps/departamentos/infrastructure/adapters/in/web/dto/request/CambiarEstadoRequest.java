package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CambiarEstadoRequest(

        @Positive(message = "El ID del departamento debe ser un número positivo")
        @NotNull(message = "El ID del departamento no puede ser nulo")
        @NotBlank(message = "El ID del departamento no puede estar vacío")
        Long departamentoId
) {
}
