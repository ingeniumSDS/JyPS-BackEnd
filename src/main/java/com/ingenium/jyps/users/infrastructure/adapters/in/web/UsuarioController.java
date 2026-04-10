package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.usecases.command.EstablecerPasswordCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.command.LoginCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.command.UpdateUsuarioCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.*;
import com.ingenium.jyps.users.application.ports.out.JwtProviderPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.*;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin("*")
@Tag(name = "2 - Usuarios", description = "Operaciones relacionadas con la gestión de usuarios y cuentas")
public class UsuarioController {

    private final GuardarUsuarioUseCase guardarUsuarioUseCase;
    private final UpdateUsuarioUseCase updateUsuarioUseCase;
    private final UpdateEstadoCuentaUseCase updateEstadoCuentaUseCase;
    private final EstablecerPasswordUseCase establecerPasswordUseCase;
    private final GenerarTokenUseCase generarTokenUseCase;
    private final LoginUseCase loginUseCase;
    private final JwtProviderPort jwtProviderPort;
    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registra un nuevo usuario", description = "Crea un nuevo usuario con la información proporcionada (Ej. Nombre, apellidos, correo, teléfono, horarios, roles y departamento) y devuelve los datos del usuario registrado junto con la ubicación del recurso creado")

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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Recupera los datos de un usuario específico según su ID")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        Usuario usuario = consultarUsuariosUseCase.obtenerPorId(id).map(u -> {
            u.setNombreDepartamento(consultarUsuariosUseCase.obtenerPorId(u.getId())
                    .map(Usuario::getNombreDepartamento)
                    .orElse("Sin departamento asignado"));
            return u;
        }).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));
        UsuarioResponse response = UsuarioResponse.desdeDominio(usuario);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener todos los usuarios", description = "Recupera una lista de todos los usuarios registrados en el sistema")
    public ResponseEntity<List<UsuarioResponse>> findAll() {

        List<Usuario> usuarios = consultarUsuariosUseCase.obtenerTodos();

        List<UsuarioResponse> response = usuarios.stream()
                .map(UsuarioResponse::desdeDominio)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos personales del usuario", description = "Realiza un update de la información del usuario (Ej. Nombre, apellidos, correo, teléfono, horarios, roles y departamento) según su ID y retorna el objeto actualizado con su ubicación")
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
    @Operation(summary = "Actualizar estado de la cuenta", description = "Realiza un toggle del estado (Activo/Inactivo) de la cuenta del usuario")
    public ResponseEntity<EstadoCuentaResponse> cambiarEstado(@PathVariable Long id) {

        updateEstadoCuentaUseCase.ejecutar(id);

        Usuario usuario = consultarUsuariosUseCase.obtenerPorId(id).map(u -> {
            u.setNombreDepartamento(consultarUsuariosUseCase.obtenerPorId(u.getId())
                    .map(Usuario::getNombreDepartamento)
                    .orElse("Sin departamento asignado"));
            return u;
        }).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe")
        );

        EstadoCuentaResponse response = EstadoCuentaResponse.desdeDominio(usuario);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/cuenta")
    @Operation(summary = "Obtener datos de la cuenta del usuario", description = "Recupera los datos de la cuenta de un usuario específico según su ID")
    public ResponseEntity<CuentaResponse> getCuenta(@PathVariable Long id) {
        Usuario usuario = consultarUsuariosUseCase.obtenerPorId(id).map(u -> {
            u.setNombreDepartamento(consultarUsuariosUseCase.obtenerPorId(u.getId())
                    .map(Usuario::getNombreDepartamento)
                    .orElse("Sin departamento asignado"));
            return u;
        }).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));
        CuentaResponse response = CuentaResponse.desdeDominio(usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/setup/validar")
    @Operation(summary = "Control de acceso a formulario", description = "Valida el token de acceso proporcionado y devuelve el token de acceso asociado a la cuenta del usuario si el token es válido. Este endpoint se utiliza para verificar la validez del token antes de permitir al usuario establecer o restablecer su contraseña.")
    public ResponseEntity<String> validarTokenAcceso(@RequestParam String token) {
        Usuario usuario = establecerPasswordUseCase.validarToken(token);
        return ResponseEntity.ok(usuario.getCuenta().getTokenAcceso());
    }

    @PostMapping("/setup")
    @Operation(summary = "Ruta que recibe la nueva contraseña", description = "Valida el token de acceso proporcionado, establece la contraseña para la cuenta del usuario asociado al token y devuelve los datos actualizados de la cuenta. Este endpoint se utiliza tanto para configurar la contraseña por primera vez después del registro como para restablecerla en caso de olvido.")
    public ResponseEntity<CuentaResponse> establecerPassword(@RequestBody EstablecerPasswordRequest request) {

        EstablecerPasswordCommand command = new EstablecerPasswordCommand(
                request.token(),
                request.password()
        );

        Usuario usuario = establecerPasswordUseCase.ejecutar(command);
        CuentaResponse cuentaResponse = CuentaResponse.desdeDominio(usuario);
        return ResponseEntity.ok(cuentaResponse);
    }

    @PostMapping("/token")
    @Operation(summary = "Token para recuperar contraseña", description = "Genera un nuevo token de acceso para el usuario asociado al correo proporcionado y lo envía por correo electrónico. Este token es de un solo uso y recuperar la contraseña.")
    public ResponseEntity<GenerarTokenResponse> generarToken(@RequestBody GenerarTokenRequest request) {

        if (request.correo() == null || request.correo().isEmpty()) {
            return ResponseEntity.badRequest().body(new GenerarTokenResponse("El correo es obligatorio"));
        }

        generarTokenUseCase.ejecutar(request.correo());

        return ResponseEntity.ok(new GenerarTokenResponse("Token generado y enviado al correo: " + request.correo()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Valida las credenciales proporcionadas (correo y contraseña) y devuelve los datos del usuario junto con un token de acceso si las credenciales son correctas. Este endpoint se utiliza para autenticar a los usuarios y permitirles acceder a los recursos protegidos del sistema.")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest request) {

        LoginCommand command = new LoginCommand(
                request.correo(),
                request.password()
        );

        if (request.correo() == null || request.correo().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        if (request.password() == null || request.password().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }


        Usuario usuario = loginUseCase.ejecutar(command);
        String tokenJwt = jwtProviderPort.generarToken(usuario);

        JWTResponse response = JWTResponse.desdeDominio(tokenJwt);

        return ResponseEntity.ok(response);
    }


}
