package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

public record LoginRequest(
        String correo,
        String password
) {
}
