package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.application.ports.out.EmailSenderPort;
import com.ingenium.jyps.users.domain.event.TokenSolicitadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecuperarPasswordListener {

    private final EmailSenderPort emailSenderPort;

    @Value("${front.end.url}")
    private String frontURL;


    @Async //  Siendo asíncrono para no trabar la API
    @EventListener
    public void alSolicitarToken(TokenSolicitadoEvent event) {

        String recuperacionRuta = "establecer-contrasena/";
        String deepLink = frontURL  + recuperacionRuta + event.tokenAcceso();

        // Delegamos el trabajo pesado al adaptador
        emailSenderPort.enviarCorreoRecuperacion(
                event.correo(),
                event.nombreCompleto(),
                deepLink
        );
    }
}
