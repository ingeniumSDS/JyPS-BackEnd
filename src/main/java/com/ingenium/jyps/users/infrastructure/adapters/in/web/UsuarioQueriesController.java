package com.ingenium.jyps.users.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class UsuarioQueriesController {

    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;

    // Obtiene los usuario por Departamento
    @GetMapping("/{departamentoId}/usuarios")
    @Operation(summary = "Filtra usuarios por depatamento", description = "Recopila a todos los usuarios de un departamento.")
    public ResponseEntity<List<UsuarioResponse>> filtrarPorDepartamento(@PathVariable Long departamentoId) {

        List<Usuario> usuarios = consultarUsuariosUseCase.filtrarPorDepartamento(departamentoId);

        List<UsuarioResponse> response = usuarios.stream().map(
                        UsuarioResponse::desdeDominio)
                .toList();


        return ResponseEntity.ok(response);
    }


}
