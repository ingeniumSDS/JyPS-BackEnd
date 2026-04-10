package com.ingenium.jyps.departamentos.application.usecase;

import com.ingenium.jyps.departamentos.application.ports.in.command.UpdateDepartamentoCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;

public interface ActualizarDepartamentoUseCase {
    Departamento ejecutar(UpdateDepartamentoCommand command);
}
