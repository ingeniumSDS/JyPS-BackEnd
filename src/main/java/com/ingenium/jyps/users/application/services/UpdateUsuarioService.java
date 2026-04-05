package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.UpdateUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUsuarioService implements UpdateUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;


    @Override
    public Usuario ejecutar(UpdateUsuarioCommand command) {

        // 1. Buscar al usuario a actualizar
        Usuario u = usuarioRepositoryPort.buscarPorId(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Validar Departamento (¡Usando el ID correcto!)
        // Solo validamos si el Frontend nos envió un departamentoId para actualizar
        if (command.departamentoId() != null) {
            departamentoRepositoryPort.findById(command.departamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        }

        // 3. Validar Correo Único (Evitando la trampa del PUT)
        if (command.correo() != null && !command.correo().isBlank()) {
            usuarioRepositoryPort.buscarPorCorreo(command.correo())
                    .ifPresent(usuarioExistente -> {
                        // Si encontramos el correo, pero el ID del dueño es diferente al mío... ¡Error!
                        if (!usuarioExistente.getId().equals(command.id())) {
                            throw new IllegalArgumentException("El correo ya existe en el sistema y pertenece a otro usuario.");
                        }
                    });
        }

        // 4. Validar Teléfono Único (Misma lógica)
        if (command.telefono() != null && !command.telefono().isBlank()) {
            usuarioRepositoryPort.buscarPorTelefono(command.telefono())
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
                command.departamentoId()
        );

        u.setNombreDepartamento(
                departamentoRepositoryPort.findById(command.departamentoId())
                        .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"))
                        .getNombre()
        );

        usuarioRepositoryPort.crear(u);

        u.setId(command.id());
        // 6. Guardar los cambios
        return u;
    }
}
