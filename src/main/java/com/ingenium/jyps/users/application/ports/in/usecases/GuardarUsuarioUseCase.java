package com.ingenium.jyps.users.application.ports.in.usecases;


import com.ingenium.jyps.users.application.ports.in.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.domain.model.Usuario;

public interface GuardarUsuarioUseCase {
    Usuario ejecutar(RegistrarUsuarioCommand command);
}
