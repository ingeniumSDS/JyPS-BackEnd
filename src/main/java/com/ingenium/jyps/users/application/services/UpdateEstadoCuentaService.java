package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.UpdateEstadoCuentaUseCase;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateEstadoCuentaService implements UpdateEstadoCuentaUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    UpdateEstadoCuentaService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Cuenta ejecutar(Long usuarioId) { // Recibe el ID
        Usuario usuario = usuarioRepositoryPort.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Cuenta cuenta = usuario.getCuenta();

        // La lógica de "Cómputo" se queda aquí
        if (cuenta.isActiva()) {
            cuenta.inactivarCuenta();
        } else {
            cuenta.activarCuenta();
        }

        usuarioRepositoryPort.save(usuario);
        return cuenta; // Devolvemos el objeto actualizado
    }

}
