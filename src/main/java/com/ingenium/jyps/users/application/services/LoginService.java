package com.ingenium.jyps.users.application.services; // Asegúrate del paquete correcto

import com.ingenium.jyps.users.application.ports.in.usecases.LoginUseCase;
import com.ingenium.jyps.users.application.ports.out.JwtProviderPort;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;


    public LoginService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort,
                        JwtProviderPort jwtProviderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario ejecutar(String correo, String password) {
        if (correo == null || password == null) {
            throw new IllegalArgumentException("Correo y contraseña no pueden ser nulos");
        }

        // 1. Buscamos al usuario (Si no existe, lanzamos el error genérico)
        Usuario usuario = usuarioRepositoryPort.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        // 2. Comparamos la contraseña plana con el Hash de la Base de Datos
        boolean esPasswordCorrecta = passwordEncoderPort.validarPassword(
                password,
                usuario.getCuenta().getPassword()
        );

        if (!esPasswordCorrecta) {
            // TODO en el futuro: Sumar un intento fallido para bloquear la cuenta tras 3 errores
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        // 3. Ejecutamos las reglas de negocio del Dominio (Revisa que esté activa, no bloqueada, etc.)
        usuario.getCuenta().login();

        // 4. Todo un éxito, retornamos la cuenta
        return usuario;
    }
}