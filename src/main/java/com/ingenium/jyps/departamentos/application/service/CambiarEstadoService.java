package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.CambiarEstadoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CambiarEstadoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CambiarEstadoService implements CambiarEstadoUseCase {

    private final DepartamentoRepositoryPort departamentoRepository;
    private final UsuarioRepositoryPort usuarioRepositoryPort;


    @Override
    public Departamento ejecutar(CambiarEstadoCommand command) {
        Departamento departamento = departamentoRepository.buscarPorId(command.departamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        long totalEmpleados = usuarioRepositoryPort.contarPorDepartamento(departamento.getId());

        if (totalEmpleados > 0 && departamento.isActivo()) {
            throw new RuntimeException("No se puede desactivar el departamento porque tiene empleados asignados");
        }

       departamento.cambiarEstado();

        return departamentoRepository.guardar(departamento);
    }
}
