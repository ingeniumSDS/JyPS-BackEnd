package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.usecase.ListarDepartamentosUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarDepartamentoService implements ListarDepartamentosUseCase {

    DepartamentoRepositoryPort repositoryPort;

    public ListarDepartamentoService(DepartamentoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Departamento> ejecutar() {
       return repositoryPort.findAll();
    }
}
