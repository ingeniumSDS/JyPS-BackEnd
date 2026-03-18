package com.ingenium.jyps.departamentos.application.ports.in;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

public interface CrearDepartamentoUseCase {
    Departamento ejecutar(CrearDepartamentoCommand command);
}
