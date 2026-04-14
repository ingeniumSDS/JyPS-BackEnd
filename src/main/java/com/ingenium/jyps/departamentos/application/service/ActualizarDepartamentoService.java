package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.UpdateDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.ActualizarDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class ActualizarDepartamentoService implements ActualizarDepartamentoUseCase {

    private final DepartamentoRepositoryPort repositoryPort;

    @Override
    public Departamento ejecutar(UpdateDepartamentoCommand command) {

        Departamento departamento = repositoryPort.buscarPorId(command.id());

        if (repositoryPort.buscarPorNombre(command.nombre()).isPresent() &&
                !repositoryPort.buscarPorNombre(command.nombre()).get().getId().equals(command.id())) {
            throw new IllegalArgumentException("Ya existe un departamento con ese nombre.");
        }


        departamento.actualizarDatos(
                command.nombre(),
                command.descripcion(),
                command.activo(),
                command.jefeId()
        );

        return repositoryPort.guardar(departamento);
    }
}
