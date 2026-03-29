package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.application.ports.in.usecases.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.domain.model.Usuario;

public interface UpdateUsuarioUseCase {
    Usuario ejecutar(UpdateUsuarioCommand command);
}
