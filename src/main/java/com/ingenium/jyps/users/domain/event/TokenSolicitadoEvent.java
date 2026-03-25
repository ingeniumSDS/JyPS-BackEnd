package com.ingenium.jyps.users.domain.event;

public record TokenSolicitadoEvent
        (String nombreCompleto,
         String correo,
         String tokenAcceso
        ) {}
