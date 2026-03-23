package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.GuardarUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuardarUsuarioService implements GuardarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    public GuardarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public Usuario ejecutar(RegistrarUsuarioCommand command) {

        Departamento departamento = departamentoRepositoryPort.findById(command.departamentoId())
                .orElseThrow(() -> new IllegalArgumentException("El departamento seleccionado no existe"));

        if (usuarioRepositoryPort.existsByCorreo(command.correo())) {
            throw new IllegalArgumentException("El correo: " + command.correo() + " ya se encuentra registrado.");
        }

        if (usuarioRepositoryPort.existsByTelefono(command.telefono())) {
            throw new IllegalArgumentException("El teléfono: " + command.telefono() + " ya se encuentra registrado.");
        }

        Usuario u = new Usuario(
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

        u.asignarCuenta(cuenta);

        return usuarioRepositoryPort.save(u);


    }
}
