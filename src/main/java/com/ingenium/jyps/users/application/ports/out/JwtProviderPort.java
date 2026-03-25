package com.ingenium.jyps.users.application.ports.out;

import com.ingenium.jyps.users.domain.model.Usuario;

public interface JwtProviderPort {
    String generarToken(Usuario usuario);
}
