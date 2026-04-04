package com.ingenium.jyps.users.application.services; // Asegúrate del paquete correcto

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.command.LoginCommand;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements com.ingenium.jyps.users.application.ports.in.usecases.LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    public LoginService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public Usuario ejecutar(LoginCommand loginCommand) {

        String correo = loginCommand.correo();
        String password = loginCommand.password();

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
            usuario.getCuenta().registrarIntentoFallido();
            usuarioRepositoryPort.guardar(usuario);
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }


        // 3. Ejecutamos las reglas de negocio del Dominio (Revisa que esté activa, no bloqueada, etc.)
        usuario.getCuenta().login();

        usuarioRepositoryPort.guardar(usuario);

        usuario.setNombreDepartamento(
                departamentoRepositoryPort.findById(usuario.getDepartamentoId())
                        .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"))
                        .getNombre()
        );
        // 4. Todo un éxito, retornamos la cuenta
        return usuario;
    }
}