package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;
import java.util.List;

public record UpdateUsuarioRequest(

        @NotNull(message = "El campo 'nombre' es obligatorio")
        @NotBlank(message = "El campo 'nombre' no puede estar vacío")
        String nombre,

        @NotNull(message = "El apellido paterno es obligatorio")
        @NotBlank(message = "El apellido paterno no puede estar vacío")
        String apellidoPaterno,

        String apellidoMaterno,

        @NotNull(message = "El campo 'correo' es obligatorio")
        @NotBlank(message = "El campo 'correo' no puede estar vacío")
        @Email(message = "El campo 'correo' debe ser un correo electrónico válido")
        String correo,

        @NotNull(message = "El campo 'telefono' es obligatorio")
        @NotBlank(message = "El campo 'telefono' no puede estar vacío")
        String telefono,

        @NotNull(message = "El campo 'hora de entrada' es obligatorio")
        @NotBlank(message = "El campo 'hora de entrada' no puede estar vacío")
        LocalTime horaEntrada,

        @NotNull(message = "El campo 'hora de salida' es obligatorio")
        @NotBlank(message = "El campo 'hora de salida' no puede estar vacío")
        LocalTime horaSalida,

        @NotNull(message = "El campo 'roles' es obligatorio")
        @NotBlank(message = "El campo 'roles' no puede estar vacío")
        @Length(min = 1, message = "El usuario debe tener al menos un rol")
        List<String> roles,

        @Positive(message = "El campo 'departamentoId' debe ser un número positivo")
        @NotNull(message = "El campo 'departamentoId' es obligatorio")
        @NotBlank(message = "El campo 'departamentoId' no puede estar vacío")
        Long departamentoId
) {
}
