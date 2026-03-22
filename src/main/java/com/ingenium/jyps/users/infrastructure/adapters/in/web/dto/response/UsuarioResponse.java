package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.time.LocalTime;
import java.util.List;

public record UsuarioResponse(
        Long id,
        String nombreCompleto, // ¡Digerido para la vista!
        String correo,
        String telefono,
        LocalTime horaEntrada,
        LocalTime horaSalida,
        List<String> roles, // Devolvemos Strings, no el Enum puro
        Long departamentoId
) {

    // 💡 TRUCO SENIOR: Método de fábrica estático (Mapper integrado)
    public static UsuarioResponse desdeDominio(Usuario usuario) {

        // Manejo seguro del apellido materno por si es nulo
        String materno = (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isBlank())
                ? " " + usuario.getApellidoMaterno()
                : "";

        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidoPaterno() + materno;

        // Convertimos la lista de Enums a lista de Strings
        List<String> rolesString = usuario.getRoles().stream()
                .map(Enum::name)
                .toList();

        return new UsuarioResponse(
                usuario.getId(),
                nombreCompleto,
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getHoraEntrada(),
                usuario.getHoraSalida(),
                rolesString,
                usuario.getDepartamentoId()
        );
    }
}