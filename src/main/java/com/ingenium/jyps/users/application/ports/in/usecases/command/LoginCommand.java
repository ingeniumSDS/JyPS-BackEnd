package com.ingenium.jyps.users.application.ports.in.usecases.command;

public record LoginCommand(
        String correo,
        String password
) {
}
