package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.command.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.application.ports.in.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.RegistrarUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.CrearUsuarioRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin("*")
@RestControllerAdvice
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase;
    private final ObtenerTodosLosUsuariosUseCase obtenerTodosLosUsuariosUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                             ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase,
                             ObtenerTodosLosUsuariosUseCase obtenerTodosLosUsuariosUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.obtenerUsuarioPorIdUseCase = obtenerUsuarioPorIdUseCase;
        this.obtenerTodosLosUsuariosUseCase = obtenerTodosLosUsuariosUseCase;
    }

    @PostMapping("")
    public ResponseEntity<Void> registrarUsuario(@Valid @RequestBody CrearUsuarioRequest request) {

        List<Roles> roles = request.roles().stream()
                .map(Roles::valueOf)
                .toList();

        RegistrarUsuarioCommand command = new RegistrarUsuarioCommand(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.correo(),
                request.telefono(),
                request.horaEntrada(),
                request.horaSalida(),
                roles,
                request.departamentoId()
        );

        Usuario nuevoUsuario = registrarUsuarioUseCase.registrarUsuario(command);

        URI location = URI.create("/api/v1/usuarios/" + nuevoUsuario.getId());

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {

        // 1. Armamos el Command con el ID que extrajimos de la URL
        ObtenerUsuarioPorIdCommand command = new ObtenerUsuarioPorIdCommand(id);

        // 2. Ejecutamos el caso de uso y capturamos al usuario
        Usuario usuario = obtenerUsuarioPorIdUseCase.obtenerUsuarioPorId(command);

        // 3. Devolvemos un 200 OK con el usuario en el cuerpo de la respuesta
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = obtenerTodosLosUsuariosUseCase.findAll();
        return ResponseEntity.ok(usuarios);
    }


}
