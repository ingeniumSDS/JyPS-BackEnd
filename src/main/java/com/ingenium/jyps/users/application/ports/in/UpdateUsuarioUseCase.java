package com.ingenium.jyps.users.application.ports.in;

import com.ingenium.jyps.users.domain.model.Usuario;

public interface UpdateUsuarioUseCase {
    Usuario ejecutar(UpdateUsuarioCommand command);
}
