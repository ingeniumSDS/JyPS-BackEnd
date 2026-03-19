package com.ingenium.jyps.departamentos.application.ports.in;

// Usamos un record porque es inmutable y perfecto para transferir datos
public record CrearDepartamentoCommand(
        String nombre,
        String descripcion,
        Long jefeId
) {}