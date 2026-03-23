package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.*;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.CrearUsuarioRequest;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.UpdateUsuarioRequest;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.CuentaResponse;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.EstadoCuentaResponse;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final GuardarUsuarioUseCase guardarUsuarioUseCase;
    private final ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase;
    private final ObtenerTodosLosUsuariosUseCase obtenerTodosLosUsuariosUseCase;
    private final UpdateUsuarioUseCase updateUsuarioUseCase;
    private final UpdateEstadoCuentaUseCase updateEstadoCuentaUseCase;

    public UsuarioController(GuardarUsuarioUseCase guardarUsuarioUseCase,
                             ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase,
                             ObtenerTodosLosUsuariosUseCase obtenerTodosLosUsuariosUseCase,
                             UpdateUsuarioUseCase updateUsuarioUseCase, UpdateEstadoCuentaUseCase updateEstadoCuentaUseCase) {

        this.guardarUsuarioUseCase = guardarUsuarioUseCase;

        this.obtenerUsuarioPorIdUseCase = obtenerUsuarioPorIdUseCase;

        this.obtenerTodosLosUsuariosUseCase = obtenerTodosLosUsuariosUseCase;

        this.updateUsuarioUseCase = updateUsuarioUseCase;

        this.updateEstadoCuentaUseCase = updateEstadoCuentaUseCase;
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

        Usuario nuevoUsuario = guardarUsuarioUseCase.ejecutar(command);

        UsuarioResponse response = UsuarioResponse.desdeDominio(nuevoUsuario);

        URI location = URI.create("/api/v1/usuarios/" + response.id());

        return ResponseEntity.created(location).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        Usuario usuario = obtenerUsuarioPorIdUseCase.ejecutar(id);
        UsuarioResponse response = UsuarioResponse.desdeDominio(usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<UsuarioResponse>> findAll() {

        List<Usuario> usuarios = obtenerTodosLosUsuariosUseCase.ejecutar();

        List<UsuarioResponse> response = usuarios.stream()
                .map(UsuarioResponse::desdeDominio)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@RequestBody UpdateUsuarioRequest request, @PathVariable Long id) {

        List<Roles> roles = request.roles().stream().map(Roles::valueOf).toList();


        UpdateUsuarioCommand command = new UpdateUsuarioCommand(
                id,
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

        Usuario usuarioActualizado = updateUsuarioUseCase.ejecutar(command);

        UsuarioResponse response = UsuarioResponse.desdeDominio(usuarioActualizado);

        return ResponseEntity.ok(response);

    }

    //Activa/Inactiva cuenta del usuario según su ID
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EstadoCuentaResponse> cambiarEstado(@PathVariable Long id) {
        Cuenta cuentaActualizada = updateEstadoCuentaUseCase.ejecutar(id);

        Usuario usuario = obtenerUsuarioPorIdUseCase.ejecutar(id);

        EstadoCuentaResponse response = EstadoCuentaResponse.desdeDominio(usuario);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/cuenta")
    public ResponseEntity<CuentaResponse> getCuenta(@PathVariable Long id) {
        Usuario usuario = obtenerUsuarioPorIdUseCase.ejecutar(id);
        CuentaResponse response = CuentaResponse.desdeDominio(usuario);
        return ResponseEntity.ok(response);
    }


}
