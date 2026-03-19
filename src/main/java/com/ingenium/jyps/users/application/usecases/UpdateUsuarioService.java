package com.ingenium.jyps.users.application.usecases;

import com.ingenium.jyps.users.application.ports.in.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.UpdateUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;

public class UpdateUsuarioService implements UpdateUsuarioUseCase {

    private final UsuarioRepositoryPort  usuarioRepositoryPort;

    public UpdateUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario ejecutar(UpdateUsuarioCommand command) {
        Usuario u = usuarioRepositoryPort.findById(command.id()).orElseThrow( () -> new  IllegalArgumentException( "Usuario no encontrado" ));

        if (usuarioRepositoryPort.findByCorreo(command.correo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya existe en el sistema.");
        }

        if (usuarioRepositoryPort.findByTelefono(command.telefono()).isPresent()) {
            throw new IllegalArgumentException("El telefono ya existe en el sistema.");
        }

        u.actualizarDatosPersonales(
                command.nombre(), command.apellidoPaterno(), command.apellidoMaterno(), command.correo(), command.telefono()
        );

        return usuarioRepositoryPort.save(u);

    }
}
