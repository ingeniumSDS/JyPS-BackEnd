package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.UpdateUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateUsuarioService implements UpdateUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    public UpdateUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public Usuario ejecutar(UpdateUsuarioCommand command) {

        // 1. Buscar al usuario a actualizar
        Usuario u = usuarioRepositoryPort.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Validar Departamento (¡Usando el ID correcto!)
        // Solo validamos si el Frontend nos envió un departamentoId para actualizar
        if (command.departamentoId() != null) {
            departamentoRepositoryPort.findById(command.departamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        }

        // 3. Validar Correo Único (Evitando la trampa del PUT)
        if (command.correo() != null && !command.correo().isBlank()) {
            usuarioRepositoryPort.findByCorreo(command.correo())
                    .ifPresent(usuarioExistente -> {
                        // Si encontramos el correo, pero el ID del dueño es diferente al mío... ¡Error!
                        if (!usuarioExistente.getId().equals(command.id())) {
                            throw new IllegalArgumentException("El correo ya existe en el sistema y pertenece a otro usuario.");
                        }
                    });
        }

        // 4. Validar Teléfono Único (Misma lógica)
        if (command.telefono() != null && !command.telefono().isBlank()) {
            usuarioRepositoryPort.findByTelefono(command.telefono())
                    .ifPresent(usuarioExistente -> {
                        if (!usuarioExistente.getId().equals(command.id())) {
                            throw new IllegalArgumentException("El teléfono ya existe en el sistema y pertenece a otro usuario.");
                        }
                    });
        }

        // 5. Mutar la entidad con tu excelente método de dominio
        u.actualizarDatosPersonales(
                command.nombre(),
                command.apellidoPaterno(),
                command.apellidoMaterno(),
                command.correo(),
                command.telefono(),
                command.horaEntrada(),
                command.horaSalida(),
                command.roles(),
                command.departamentoId() // Ya viene verificado
        );

        // 6. Guardar los cambios
        return usuarioRepositoryPort.save(u);
    }
}
