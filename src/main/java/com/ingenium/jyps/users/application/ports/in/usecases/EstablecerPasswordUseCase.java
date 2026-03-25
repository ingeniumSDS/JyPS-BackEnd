package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.ValidarTokenRequest;

public interface EstablecerPasswordUseCase {
    Usuario ejecutar (ValidarTokenRequest request);
    Usuario validarToken(String token);
}
