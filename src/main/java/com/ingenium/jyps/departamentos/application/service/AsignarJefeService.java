package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.AsignarJefeCommand;
import com.ingenium.jyps.departamentos.application.usecase.AsignarJefeUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsignarJefeService implements AsignarJefeUseCase {

    private final DepartamentoRepositoryPort repositoryPort;

    @Override
    public Departamento ejecutar(AsignarJefeCommand command) {

        Departamento departamento = repositoryPort.buscarPorId(command.id())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        departamento.asignarJefe(command.idJefe());
        return  repositoryPort.guardar(departamento);
    }

}
