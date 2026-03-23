package com.ingenium.jyps.users.domain.ports.out;

public interface PasswordEncoderPort {
    String codificar(String passwordPlana);

    boolean coinciden(String passwordPlana, String passwordHasheada);
}
