package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.application.ports.out.EmailSenderPort;
import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacionBienvenidaListener {

    private final EmailSenderPort emailSenderPort; // Inyectamos la interfaz

    @Value("${front.end.url}")
    private String frontURL;

    public NotificacionBienvenidaListener(EmailSenderPort emailSenderPort) {
        this.emailSenderPort = emailSenderPort;
    }

    @Async // Sigue siendo asíncrono para no trabar la API
    @EventListener
    public void alCrearUsuario(UsuarioCreadoEvent event) {

        String recuperacionRuta = "establecer-contrasena/";
        String deepLink = frontURL + recuperacionRuta + event.tokenAcceso();

        // Delegamos el trabajo pesado al adaptador
        emailSenderPort.enviarCorreoBienvenida(
                event.correo(),
                event.nombreCompleto(),
                deepLink
        );
    }
}