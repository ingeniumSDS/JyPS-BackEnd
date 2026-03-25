package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.EstablecerPasswordRequest;

public interface EstablecerPasswordUseCase {
    Usuario ejecutar (EstablecerPasswordRequest request);
    Usuario validarToken(String token);
}
