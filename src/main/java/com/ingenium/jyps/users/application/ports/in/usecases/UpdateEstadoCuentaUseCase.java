package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Cuenta;

public interface UpdateEstadoCuentaUseCase {
    Cuenta ejecutar(Long usuarioId);
}
