package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "El correo es obligatorio")
        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El correo debe ser válido")
        String correo,

        @NotNull(message = "La contraseña es obligatoria")
        @NotBlank(message = "El contraseña no puede estar vacía")
        String password
) {
}
