package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.UpdateEstadoCuentaUseCase;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class UpdateEstadoCuentaService implements UpdateEstadoCuentaUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    @Override
    public Cuenta ejecutar(Long usuarioId) { // Recibe el ID
        Usuario usuario = usuarioRepositoryPort.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (departamentoRepositoryPort.buscarPorJefeId(usuario.getId()).isPresent()) {
            throw new IllegalStateException("No se puede inactivar el JEFE de un departamento activo. Por favor, reasigne el departamento antes de inactivar la cuenta.");
        }


        Cuenta cuenta = usuario.getCuenta();

        // La lógica de "Cómputo" se queda aquí
        if (cuenta.isActiva()) {
            cuenta.inactivarCuenta();
        } else {
            cuenta.activarCuenta();
        }

        usuarioRepositoryPort.crear(usuario);
        return cuenta; // Devolvemos el objeto actualizado
    }

}
