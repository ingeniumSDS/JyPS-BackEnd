package com.ingenium.jyps.users.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Cuenta {
    private int intentosFallidos;
    private String tokenRecuperacion;
    private LocalDateTime tokenExpiresAt;
    private boolean tokenUsado, bloqueada, activa;
    private LocalDateTime blockedAt;
    private String password;

    public Cuenta(String tokenAcceso, int tiempoTokenEnMinutos) {
        this.password = null;
        this.intentosFallidos = 0;
        this.tokenUsado = false;
        this.bloqueada = false;
        this.activa = false;
        this.tokenExpiresAt = LocalDateTime.now().plusMinutes(tiempoTokenEnMinutos);
        this.tokenRecuperacion = tokenAcceso;
    }

    // Constructor 2: EXCLUSIVO PARA REHIDRATAR DESDE BD (Recibe datos)
    public Cuenta(String password, int intentosFallidos, String tokenRecuperacion, LocalDateTime tokenExpiresAt,
                  boolean tokenUsado, boolean bloqueada, boolean activa, LocalDateTime blockedAt) {
        this.password = password;
        this.intentosFallidos = intentosFallidos;
        this.tokenRecuperacion = tokenRecuperacion;
        this.tokenExpiresAt = tokenExpiresAt;
        this.tokenUsado = tokenUsado;
        this.bloqueada = bloqueada;
        this.activa = activa;
        this.blockedAt = blockedAt;
    }

    public void generarTokenAcceso(String tokenAcceso, int minutosValidez) {
        this.tokenRecuperacion = tokenAcceso;
        this.tokenExpiresAt = LocalDateTime.now().plusMinutes(minutosValidez);
        this.tokenUsado = false;

    }

    public void usarToken() {
        this.tokenUsado = true;
        this.tokenRecuperacion = null;
    }

    public void bloquearCuenta() {
        this.bloqueada = true;
        this.blockedAt = LocalDateTime.now();
    }

    public void inactivarCuenta() {
        this.activa = false;
    }


    public void activarCuenta() {
        this.activa = true;
    }

    public boolean estaBloqueada() {
        if (this.bloqueada) {
            // ¿Ya pasaron 2 minutos desde que se bloqueó?
            if (this.blockedAt != null && LocalDateTime.now().isAfter(this.blockedAt.plusMinutes(2))) {
                resetIntentoFallido(); // Esto quita el bloqueo y resetea los intentos
                return false; // Ya no está bloqueada
            }
            return true; // Sigue bloqueada
        }
        return false; // Nunca estuvo bloqueada
    }


    public void registrarIntentoFallido() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= 3) {
            bloquearCuenta();
        }
    }

    public void resetIntentoFallido() {
        this.intentosFallidos = 0;
        this.bloqueada = false;
        this.blockedAt = null;
    }

    public void establecerPassword(String passwordHash) {
        this.password = passwordHash;
        if (!this.activa) {
            activarCuenta();
        }
        usarToken();
    }

}
