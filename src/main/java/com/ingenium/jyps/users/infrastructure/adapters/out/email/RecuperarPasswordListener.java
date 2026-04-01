package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.application.ports.out.EmailSenderPort;
import com.ingenium.jyps.users.domain.event.TokenSolicitadoEvent;
import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RecuperarPasswordListener {

    private final EmailSenderPort emailSenderPort;

    public RecuperarPasswordListener(EmailSenderPort emailSenderPort) {
        this.emailSenderPort = emailSenderPort;
    }

    @Async //  Siendo asíncrono para no trabar la API
    @EventListener
    public void alSolicitarToken(TokenSolicitadoEvent event) {

        String deepLink = "http://localhost:5173/establecer-contrasena/:token=" + event.tokenAcceso();

        // Delegamos el trabajo pesado al adaptador
        emailSenderPort.enviarCorreoRecuperacion(
                event.correo(),
                event.nombreCompleto(),
                deepLink
        );
    }
}
