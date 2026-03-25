package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request;

public record EstablecerPasswordRequest(
        String token,
        String password
) {}
