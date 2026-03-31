package com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response;

import com.ingenium.jyps.users.domain.model.Usuario;


public record EstadoCuentaResponse(
        String nombreCompleto,
        boolean activa,
        String message
) {
    // 💡 TRUCO SENIOR: Método de fábrica estático (Mapper integrado)
    public static EstadoCuentaResponse desdeDominio(Usuario usuario) {

        // Manejo seguro del apellido materno por si es nulo
        String materno = (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isBlank())
                ? " " + usuario.getApellidoMaterno()
                : "";

        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidoPaterno() + materno;

        return new EstadoCuentaResponse(
                nombreCompleto,
                usuario.getCuenta().isActiva(),
                usuario.getCuenta().isActiva() ? "Cuenta activada" : "Cuenta inactivada"
        );
    }
}
