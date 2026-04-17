package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.CrearJefeUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.command.CrearJefeCommand;
import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearJefeService implements CrearJefeUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public Usuario ejecutar(CrearJefeCommand command) {

        List<Roles> roles = List.of(Roles.JEFE_DE_DEPARTAMENTO); // Asignamos el rol de JEFE al nuevo usuario


        // Aquí iría la lógica para crear un jefe, como validar los datos, crear el objeto Usuario, etc.
        // Por ahora, simplemente devolveremos un nuevo Usuario con los datos del comando.
        Usuario jefe = new Usuario (
                command.nombre(),
                command.apellidoPaterno(),
                command.apellidoMaterno(),
                command.correo(),
                command.telefono(),
                command.horaEntrada(),
                command.horaSalida(),
                roles,
                command.departamentoId()// departamentoId se asignaría posteriormente, quizás a través de otro proceso o comando,
        );

        String tokenAcceso = UUID.randomUUID().toString();

        Cuenta cuenta = new Cuenta(
                tokenAcceso,
                120
        );


        jefe.asignarCuenta(cuenta);

        usuarioRepositoryPort.guardar(jefe);
        jefe.setId(usuarioRepositoryPort.buscarPorCorreo(command.correo()).orElseThrow().getId());
        publisher.publishEvent(new UsuarioCreadoEvent(
                jefe.getCorreo(),
                jefe.getNombre() + " " + jefe.getApellidoPaterno() + " " + jefe.getApellidoMaterno(),
                tokenAcceso
        ));

        return jefe;
    }
}


