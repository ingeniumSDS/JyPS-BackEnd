package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.ResetearCuentaUseCase;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class ResetearCuentaService implements ResetearCuentaUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public String ejecutar(Long idUsuario) {
        Usuario usuario = usuarioRepositoryPort.buscarPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Cuenta cuenta = usuario.getCuenta();

        cuenta.establecerPassword(passwordEncoderPort.codificar("admin"));
        usuario.asignarCuenta(cuenta);

        usuarioRepositoryPort.guardar(usuario);

        return "Cuenta reseteada exitosamente. La nueva contraseña es: admin";
    }
}
