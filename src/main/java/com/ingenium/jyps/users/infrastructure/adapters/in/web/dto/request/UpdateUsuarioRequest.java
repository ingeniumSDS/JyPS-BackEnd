package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import java.util.List;

public record UpdateUsuarioRequest(
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        String telefono,
        String horaEntrada,
        String horaSalida,
        List<String> roles,
        Long departamentoId
) {
}
