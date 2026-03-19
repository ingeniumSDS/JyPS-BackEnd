package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.ports.in.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.users.application.ports.in.*;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.SpringDataUsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
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
    public ResponseEntity<Void> registrarUsuario(@RequestBody RegistrarUsuarioCommand command) {

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
