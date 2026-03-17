package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEmbeddable {
    private String password;
    private int intentosFallidos;
    private String tokenRecuperacion;
    private LocalDateTime tokenExpiresAt;
    private boolean tokenUsado;
    private boolean bloqueada;
    private boolean activa;
    private LocalDateTime blockedAt;



}