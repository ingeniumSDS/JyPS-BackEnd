package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

import java.time.LocalTime;
import java.util.List;

public record UpdateUsuarioRequest(
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        String telefono,
        LocalTime horaEntrada,
        LocalTime horaSalida,
        List<String> roles,
        Long departamentoId
) {
}
