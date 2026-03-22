package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.application.ports.in.command.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.domain.model.Usuario;

public interface ObtenerUsuarioPorIdUseCase {
    Usuario obtenerUsuarioPorId(ObtenerUsuarioPorIdCommand command);
}
