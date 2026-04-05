package com.ingenium.jyps.departamentos.application.usecase;

import com.ingenium.jyps.departamentos.application.ports.in.command.AsignarJefeCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;

public interface AsignarJefeUseCase {
    Departamento ejecutar(AsignarJefeCommand command);
}
