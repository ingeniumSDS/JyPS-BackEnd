package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

public record ValidarTokenRequest(
        String token,
        String password
) {}
