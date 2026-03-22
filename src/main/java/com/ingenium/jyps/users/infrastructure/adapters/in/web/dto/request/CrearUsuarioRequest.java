package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
        @NotNull
        @NotBlank
        LocalTime horaEntrada,
        @NotNull
        @NotBlank
        LocalTime horaSalida,
        @NotNull
        @NotBlank
        List<String> roles,
        @NotNull
        @NotBlank
        Long departamentoId
) {}
