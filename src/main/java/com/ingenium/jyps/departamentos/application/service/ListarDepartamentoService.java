package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.usecase.ListarDepartamentosUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarDepartamentoService implements ListarDepartamentosUseCase {

    private final DepartamentoRepositoryPort repositoryPort;

    @Override
    public List<Departamento> ejecutar() {
       return repositoryPort.buscarTodos();
    }
}
