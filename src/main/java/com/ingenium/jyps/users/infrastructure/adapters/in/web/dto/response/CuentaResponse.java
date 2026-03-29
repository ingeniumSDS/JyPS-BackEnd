package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.users.domain.model.Usuario;


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


        return new CuentaResponse(
                nombreCompleto,
                usuario.getCuenta().isActiva(),
                usuario.getCuenta().getIntentosFallidos(),
                usuario.getCuenta().isBloqueada()
        );
    }
}
