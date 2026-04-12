package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EstablecerPasswordRequest(
        @NotNull(message = "El token es obligatorio")
        @NotBlank(message = "El token no puede estar vacío")
        String token,

        @NotNull(message = "La contraseña es obligatoria")
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password
) {
}
