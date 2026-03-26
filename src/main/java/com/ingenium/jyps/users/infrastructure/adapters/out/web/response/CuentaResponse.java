package com.ingenium.jyps.users.infrastructure.adapters.out.web.response;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.List;

public record CuentaResponse(
        String nombreCompleto,
        boolean activa,
        int intentosFallidos,
        boolean bloqueada
) {


    // 💡 TRUCO SENIOR: Método de fábrica estático (Mapper integrado)
    public static CuentaResponse desdeDominio(Usuario usuario) {

        // Manejo seguro del apellido materno por si es nulo
        String materno = (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isBlank())
                ? " " + usuario.getApellidoMaterno()
                : "";

        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidoPaterno() + materno;

        // Convertimos la lista de Enums a lista de Strings
        List<String> rolesString = usuario.getRoles().stream()
                .map(Enum::name)
                .toList();


        return new CuentaResponse(
                nombreCompleto,
                usuario.getCuenta().isActiva(),
                usuario.getCuenta().getIntentosFallidos(),
                usuario.getCuenta().isBloqueada()
        );
    }
}
