package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.GuardarUsuarioUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuardarUsuarioService implements GuardarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public Usuario ejecutar(RegistrarUsuarioCommand command) {

        Departamento departamento = departamentoRepositoryPort.buscarPorId(command.departamentoId());

        if (usuarioRepositoryPort.existsByCorreo(command.correo())) {
            throw new IllegalArgumentException("El correo: " + command.correo() + " ya se encuentra registrado.");
        }

        if (usuarioRepositoryPort.existsByTelefono(command.telefono())) {
            throw new IllegalArgumentException("El teléfono: " + command.telefono() + " ya se encuentra registrado.");
        }

        Usuario nuevoUsuario = new Usuario(
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

        String tokenAcceso = UUID.randomUUID().toString();
        Cuenta cuenta = new Cuenta(
                tokenAcceso,
                120
        );

        nuevoUsuario.setNombreDepartamento(departamento.getNombre());
        nuevoUsuario.asignarCuenta(cuenta);

        usuarioRepositoryPort.crear(nuevoUsuario);
        nuevoUsuario.setId(usuarioRepositoryPort.buscarPorCorreo(command.correo()).orElseThrow().getId());
        publisher.publishEvent(new UsuarioCreadoEvent(
                nuevoUsuario.getCorreo(),
                nuevoUsuario.getNombre() + " " + nuevoUsuario.getApellidoPaterno() +  " " + nuevoUsuario.getApellidoMaterno(),
                tokenAcceso
        ));

        return nuevoUsuario;

    }
}
