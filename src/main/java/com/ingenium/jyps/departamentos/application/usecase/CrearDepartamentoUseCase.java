package com.ingenium.jyps.departamentos.application.usecase;

import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;

public interface CrearDepartamentoUseCase {
    Departamento ejecutar(CrearDepartamentoCommand command);
}
