package com.ingenium.jyps.departamentos.application.usecase;

import com.ingenium.jyps.departamentos.application.ports.in.command.CambiarEstadoCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;

public interface CambiarEstadoUseCase {
    Departamento ejecutar(CambiarEstadoCommand command);
}
