package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.RegistrarUsuarioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }

    @PostMapping("")
    public ResponseEntity<Void> registrarUsuario(@RequestBody RegistrarUsuarioCommand command) {

        registrarUsuarioUseCase.registrarUsuario(command);

        return ResponseEntity.created(URI.create("/api/v1/users")).build();
    }

}
