package com.ingenium.jyps.users.domain.ports.out;

public interface PasswordEncoderPort {
    String encodePassword(String password);
}
