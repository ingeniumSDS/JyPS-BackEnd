package com.ingenium.jyps.users.application.ports.in.usecases.command;

import jakarta.validation.constraints.*;

import java.time.LocalTime;


public record CrearJefeCommand(
        @NotBlank (message = "El nombre no puede ser nulo o estar vacío.")
        @NotNull (message = "El nombre no puede ser nulo.")
        String nombre,

        @NotBlank (message = "El nombre no puede ser nulo o estar vacío.")
        @NotNull (message = "El nombre no puede ser nulo.")
        String apellidoPaterno,

        String apellidoMaterno,

        @NotBlank (message = "El nombre no puede ser nulo o estar vacío.")
        @NotNull (message = "El nombre no puede ser nulo.")
        @Email (message = "El correo debe tener un formato válido.")
        String correo,

        @NotBlank (message = "El nombre no puede ser nulo o estar vacío.")
        @NotNull (message = "El nombre no puede ser nulo.")
        String telefono,

        LocalTime horaEntrada,
        LocalTime horaSalida,

        @Positive (message = "El ID del departamento debe ser un número positivo.")
        @Min(value = 1, message = "El ID del departamento debe ser mayor o igual a 1.")
        long departamentoId
) {
}
