package com.ingenium.jyps.users.infrastructure.adapters.out.web.response;

public record JWTResponse(
        String tokenJwt
) {

    public static JWTResponse desdeDominio(String tokenJwt) {
        return new JWTResponse(tokenJwt);
    }
}
