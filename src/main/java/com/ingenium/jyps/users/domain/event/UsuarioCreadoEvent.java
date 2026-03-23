package com.ingenium.jyps.users.domain.event;

public record UsuarioCreadoEvent(
        String correo,
        String nombreCompleto,
        String tokenAcceso
) {
}
