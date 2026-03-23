package com.ingenium.jyps.departamentos.application.usecase;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

import java.util.List;

public interface ListarDepartamentosUseCase {
    List<Departamento> ejecutar();
}
