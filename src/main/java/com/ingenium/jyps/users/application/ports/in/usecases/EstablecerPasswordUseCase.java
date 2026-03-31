package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.application.ports.in.usecases.command.EstablecerPasswordCommand;
import com.ingenium.jyps.users.domain.model.Usuario;

public interface EstablecerPasswordUseCase {
    Usuario ejecutar (EstablecerPasswordCommand command);
    Usuario validarToken(String token);
}
