package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.CambiarEstadoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CambiarEstadoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class CambiarEstadoService implements CambiarEstadoUseCase {

    private final DepartamentoRepositoryPort departamentoRepository;
    private final UsuarioRepositoryPort usuarioRepositoryPort;


    @Override
    public Departamento ejecutar(CambiarEstadoCommand command) {
        Departamento departamento = departamentoRepository.buscarPorId(command.departamentoId());

        long totalEmpleados = usuarioRepositoryPort.contarPorDepartamento(departamento.getId());

        if (totalEmpleados > 0 && departamento.isActivo()) {
            throw new IllegalArgumentException("No se puede desactivar el departamento porque tiene empleados asignados");
        }

       departamento.cambiarEstado();

        return departamentoRepository.guardar(departamento);
    }
}
