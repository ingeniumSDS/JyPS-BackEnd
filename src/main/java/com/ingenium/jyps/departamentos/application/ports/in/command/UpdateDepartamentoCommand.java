package com.ingenium.jyps.departamentos.application.ports.in.command;

// Usamos un record porque es inmutable y perfecto para transferir datos
public record UpdateDepartamentoCommand(
        Long id,
        String nombre,
        String descripcion,
        boolean activo,
        Long jefeId
) {

}