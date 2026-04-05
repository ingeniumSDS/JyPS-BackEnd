package com.ingenium.jyps.departamentos.application.ports.in.command;

public record AsignarJefeCommand(
        Long id,
        Long idJefe
) {
}
