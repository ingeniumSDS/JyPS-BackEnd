package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CrearDepartamentoRequest(
        @NotNull(message = "El nombre del departamento no puede ser nulo")
        @NotBlank(message = "El nombre del departamento no puede estar vacío")
        String nombre,

        @Length(max = 255, min = 25, message = "La descripción del departamento no puede exceder los 255 caracteres ni ser menor a 25 caracteres")
        @NotBlank(message = "La descripción del departamento no puede estar vacía")
        @NotNull(message = "La descripción del departamento no puede ser nula")
        String descripcion,

        Long jefeId
) {
}
