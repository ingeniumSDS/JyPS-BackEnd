package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.application.ports.in.usecases.command.CrearJefeCommand;
import com.ingenium.jyps.users.domain.model.Usuario;

public interface CrearJefeUseCase {
    Usuario ejecutar(CrearJefeCommand command);
}
