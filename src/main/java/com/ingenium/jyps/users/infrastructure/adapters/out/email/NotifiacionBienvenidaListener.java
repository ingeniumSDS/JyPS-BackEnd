package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.domain.event.UsuarioCreadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotifiacionBienvenidaListener {

    @Async
    @EventListener
    public void alCrearUsuario(UsuarioCreadoEvent event){
        System.out.println("⏳ [INICIO] Enviando correo en segundo plano a: " + event.correo());

        // Simulamos que el servidor de correos tarda 3 segundos en responder
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String deepLink = "jypsapp://auth/setup?token=" + event.tokenAcceso();
        System.out.println("✅ [FIN] Correo enviado exitosamente con link: " + deepLink);
    }
}
