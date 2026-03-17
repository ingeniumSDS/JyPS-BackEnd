package com.ingenium.jyps.users.application.usecases;

import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public RegistrarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public void registrarUsuario(RegistrarUsuarioCommand command) {

        if  (usuarioRepositoryPort.findByCorreo(command.correo()).isPresent()) {
            throw new IllegalArgumentException("El correo: " + command.correo() + " ya se encuentra registrado." );
        }

        Usuario u = new Usuario(
                command.nombre(),
                command.apellidoPaterno(),
                command.apellidoMaterno(),
                command.correo(),
                command.telefono(),
                command.horaEntrada(),
                command.horaSalida(),
                command.roles()
        );

        String tokenAcceso = UUID.randomUUID().toString();

        Cuenta cuenta = new Cuenta(
                tokenAcceso,
                120
        );

        u.asignarCuenta(cuenta);

        usuarioRepositoryPort.save(u);

    }
}
