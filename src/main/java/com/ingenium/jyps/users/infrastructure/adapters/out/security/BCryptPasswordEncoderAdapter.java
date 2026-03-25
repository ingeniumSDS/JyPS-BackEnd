package com.ingenium.jyps.users.infrastructure.adapters.out.security;

import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();

    @Override
    public String codificar(String passwordPlana) {
        return encoder.encode(passwordPlana);
    }

    @Override
    public boolean validarPassword(String passwordPlana, String passwordHasheada) {
        return (encoder.matches(passwordPlana, passwordHasheada));
    }
}
