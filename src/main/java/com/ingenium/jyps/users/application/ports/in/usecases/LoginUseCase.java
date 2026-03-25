package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;

public interface LoginUseCase {
    Usuario ejecutar(String correo, String password);
}
