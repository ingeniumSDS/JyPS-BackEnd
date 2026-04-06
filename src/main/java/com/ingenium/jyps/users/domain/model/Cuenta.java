package com.ingenium.jyps.users.domain.model;

import com.ingenium.jyps.config.mappstruct.Default;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Cuenta {
    private int intentosFallidos;
    private String tokenAcceso;
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
        this.tokenAcceso = tokenAcceso;
    }

    @Default
    public Cuenta(String password, int intentosFallidos, String tokenAcceso, LocalDateTime tokenExpiresAt,
                  boolean tokenUsado, boolean bloqueada, boolean activa, LocalDateTime blockedAt) {
        this.password = password;
        this.intentosFallidos = intentosFallidos;
        this.tokenAcceso = tokenAcceso;
        this.tokenExpiresAt = tokenExpiresAt;
        this.tokenUsado = tokenUsado;
        this.bloqueada = bloqueada;
        this.activa = activa;
        this.blockedAt = blockedAt;
    }

    public boolean primerInicio() {
        return password == null;
    }

    public void generarTokenAcceso() {
        if (!primerInicio()) {
            estaInactiva();
        }
        this.tokenAcceso = UUID.randomUUID().toString();
        this.tokenExpiresAt = LocalDateTime.now().plusMinutes(120);
        this.tokenUsado = false;

    }

    public void usarToken() {
        this.tokenUsado = true;
        this.tokenAcceso = null;
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

    public void estaInactiva() {
        if (!this.activa) {
            throw new IllegalStateException("La cuenta no está activa. Si cree que se trata de un error contacte al administrador del sistema..");
        }
    }

    public void estaBloqueada() {
        if (this.bloqueada) {
            if (this.blockedAt != null && LocalDateTime.now().isAfter(this.blockedAt.plusMinutes(2))) {
                resetIntentoFallido();// Esto quita el bloqueo y resetea los intentos
            } else {
                throw new IllegalStateException("La cuenta está bloqueada debido a múltiples intentos fallidos. Por favor, inténtelo de nuevo más tarde.");
            }
        }
    }


    public void registrarIntentoFallido() {
        estaBloqueada();
        estaInactiva();
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
        activarCuenta();
        resetIntentoFallido();
        usarToken();
    }

    public void validarToken() {

        if (this.tokenUsado) {
            throw new IllegalStateException("El token ya ha sido utilizado. Solicite uno nuevo.");
        }

        if (LocalDateTime.now().isAfter(this.tokenExpiresAt)) {
            throw new IllegalStateException("El token ha expirado. Solicite uno nuevo.");
        }

    }

    public void login() {

        if (this.password == null) {
            throw new IllegalStateException("La cuenta no tiene contraseña establecida. Por favor, revise su correo institucional.");
        }

        estaBloqueada();
        estaInactiva();
        resetIntentoFallido();
    }

    public void esPasswordSegura(String passwordPlana) {

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[#$!@%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/~`]).{12,}$";

        if (passwordPlana.length() < 12) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 12 caracteres.");
        }

        if (!passwordPlana.matches(passwordRegex)) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.");
        }

    }

}
