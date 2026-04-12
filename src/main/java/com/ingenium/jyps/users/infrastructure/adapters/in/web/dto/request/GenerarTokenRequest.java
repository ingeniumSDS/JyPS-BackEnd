package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record  GenerarTokenRequest (
        @NotNull(message = "El correo electrónico no puede ser nulo")
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "El correo electrónico no es válido")
        String correo
) {
}
