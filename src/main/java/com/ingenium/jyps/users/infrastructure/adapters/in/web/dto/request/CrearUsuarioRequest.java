package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record CrearUsuarioRequest(
        @NotNull(message = "El nombre es obligatorio")
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El apellido paterno es obligatorio")
        @NotBlank(message = "El apellido paterno no puede estar vacío")
        String apellidoPaterno,

        String apellidoMaterno,

        @NotNull(message = "El correo es obligatorio")
        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El correo debe ser una dirección de correo electrónico válida")
        String correo,

        @NotNull(message = "El teléfono es obligatorio")
        @NotBlank(message = "El teléfono no puede estar vacío")
        String telefono,

        @NotNull(message = "El campo 'hora de entrada' es obligatorio")
        LocalTime horaEntrada,

        @NotNull(message = "El campo 'hora de salida' es obligatorio")
        LocalTime horaSalida,

        List<String> roles,

        @NotNull(message = "El id del departamento no puede ser nulo")
        Long departamentoId
) {
}