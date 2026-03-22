package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.UpdateUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepository;

public class UpdateUsuarioService implements UpdateUsuarioUseCase {

    private final UsuarioRepository usuarioRepositoryPort;

    public UpdateUsuarioService(UsuarioRepository usuarioRepositoryPort) {
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
