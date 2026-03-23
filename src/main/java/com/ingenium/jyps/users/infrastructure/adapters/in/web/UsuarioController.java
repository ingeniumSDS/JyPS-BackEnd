package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.command.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.application.ports.in.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.RegistrarUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.CrearUsuarioRequest;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.UpdateUsuarioRequest;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin("*")
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
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody CrearUsuarioRequest request) {

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

        UsuarioResponse response = UsuarioResponse.desdeDominio(nuevoUsuario);

        URI location = URI.create("/api/v1/usuarios/" + response.id());

        return ResponseEntity.created(location).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {

        ObtenerUsuarioPorIdCommand command = new ObtenerUsuarioPorIdCommand(id);

        Usuario usuario = obtenerUsuarioPorIdUseCase.obtenerUsuarioPorId(command);

        UsuarioResponse response = UsuarioResponse.desdeDominio(usuario);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = obtenerTodosLosUsuariosUseCase.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario (@RequestBody UpdateUsuarioRequest request, @PathVariable Long id) {

        Usuario usuarioExistente = obtenerUsuarioPorIdUseCase.obtenerUsuarioPorId(new ObtenerUsuarioPorIdCommand(id));
        return null;
    }


}
