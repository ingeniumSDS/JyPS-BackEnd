package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.ports.in.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.users.application.ports.in.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.application.ports.in.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                             ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.obtenerUsuarioPorIdUseCase = obtenerUsuarioPorIdUseCase;
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


}
