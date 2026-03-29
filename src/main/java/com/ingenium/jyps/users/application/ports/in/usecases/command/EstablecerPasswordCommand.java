package com.ingenium.jyps.users.application.ports.in.usecases.command;

public record EstablecerPasswordCommand(
        String token,
        String password) {

}
