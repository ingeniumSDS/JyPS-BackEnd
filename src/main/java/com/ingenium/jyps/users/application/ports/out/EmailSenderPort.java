package com.ingenium.jyps.users.application.ports.out;

public interface EmailSenderPort {
    void enviarCorreoBienvenida(String destinatario, String nombre, String deepLink);
    void enviarCorreoRecuperacion(String destinatario, String nombre, String deepLink);
}