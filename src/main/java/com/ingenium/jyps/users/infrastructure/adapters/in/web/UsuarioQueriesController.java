package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1")
@Tag(name = "3 - Usuarios Queries Personalizadas", description = "Queries para filtros relacionadas a usuarios.")

public class UsuarioQueriesController {

    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;

    // Obtiene los usuarios por Departamento
    @GetMapping("/{departamentoId}/usuarios")
    @Operation(summary = "Filtra usuarios por depatamento", description = "Recopila a todos los usuarios de un departamento.")
    public ResponseEntity<List<UsuarioResponse>> filtrarPorDepartamento(@PathVariable Long departamentoId) {

        List<Usuario> usuarios = consultarUsuariosUseCase.filtrarPorDepartamento(departamentoId);

        List<UsuarioResponse> response = usuarios.stream().map(
                        UsuarioResponse::desdeDominio)
                .toList();


        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuarios/jefes")
    @Operation(summary = "Filtro Jefes de Departamento", description = "Recopila a todos los usuarios que sean jefes de departamento.")
    public ResponseEntity<List<UsuarioResponse>> filtrarJefes() {

        List<Usuario> usuarios = consultarUsuariosUseCase.filtrarJefes();

        List<UsuarioResponse> response = usuarios.stream().map(
                        UsuarioResponse::desdeDominio)
                .toList();

        return ResponseEntity.ok(response);

    }
}
