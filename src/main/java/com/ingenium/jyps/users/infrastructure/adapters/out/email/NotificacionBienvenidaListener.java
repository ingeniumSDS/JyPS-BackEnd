package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.application.ports.out.EmailSenderPort;
import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NotificacionBienvenidaListener {

    private final EmailSenderPort emailSenderPort; // Inyectamos la interfaz

    public NotificacionBienvenidaListener(EmailSenderPort emailSenderPort) {
        this.emailSenderPort = emailSenderPort;
    }

    @Async // Sigue siendo asíncrono para no trabar la API
    @EventListener
    public void alCrearUsuario(UsuarioCreadoEvent event) {

        String deepLink = "http://localhost:5173/establecer-contrasena/:token=" + event.tokenAcceso();

        // Delegamos el trabajo pesado al adaptador
        emailSenderPort.enviarCorreoBienvenida(
                event.correo(),
                event.nombreCompleto(),
                deepLink
        );
    }
}