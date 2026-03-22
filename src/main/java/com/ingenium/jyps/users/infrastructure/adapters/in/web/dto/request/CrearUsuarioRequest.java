package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;
import java.util.List;

public record CrearUsuarioRequest(
        @NotNull
        @NotBlank
        String nombre,
        @NotNull
        @NotBlank
        String apellidoPaterno,
        @NotNull
        @NotBlank
        String apellidoMaterno,
        @NotNull
        @NotBlank
        String correo,
        @NotNull
        @NotBlank
        String telefono,
        @NotBlank
        LocalTime horaEntrada,
        @NotBlank
        LocalTime horaSalida,
        @NotBlank
        @Length(min = 1)
        List<String> roles,
        @NotBlank
        Long departamentoId
) {}
