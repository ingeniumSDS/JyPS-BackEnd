package com.ingenium.jyps.users.infrastructure.adapters.out.email;

import com.ingenium.jyps.users.application.ports.out.EmailSenderPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JavaMailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender mailSender; // 🔌 Inyectamos la herramienta de Spring
    @Value("${spring.mail.username}")
    private String remitente;

    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombre, String deepLink) {
        try {
            // Usamos MimeMessage para poder enviar HTML, no solo texto plano
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido a JYPS! Completa tu registro 🚀");

            // Construimos el HTML
            String contenidoHtml = """
                    <div style="background-color: #f4f4f7; padding: 40px 20px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; text-align: center;">
                        <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 40px; border-radius: 8px; border: 1px solid #e1e1e1; box-shadow: 0 4px 10px rgba(0,0,0,0.05);">
                    
                            <h1 style="color: #007bff; margin-top: 0; margin-bottom: 20px; font-size: 28px; letter-spacing: 1px;">
                                JyPS
                            </h1>
                    
                            <h2 style="color: #2c3e50; font-size: 22px; margin-bottom: 15px;">¡Hola, %s! 👋</h2>
                    
                            <p style="color: #555555; font-size: 16px; line-height: 1.6; margin-bottom: 20px;">
                                Te damos la bienvenida al equipo. Tu cuenta en la plataforma ha sido creada exitosamente.
                            </p>
                            <p style="color: #555555; font-size: 16px; line-height: 1.6; margin-bottom: 30px;">
                                Para proteger tu información y acceder al sistema, por favor establece tu contraseña haciendo clic en el siguiente botón:
                            </p>
                    
                            <a href="%s" style="background-color: #007bff; color: #ffffff; padding: 14px 30px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px; display: inline-block;">
                                Establecer mi contraseña
                            </a>
                    
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #eeeeee;">
                                <p style="color: #888888; font-size: 13px; line-height: 1.5; margin-bottom: 10px;">
                                    <em>⚠️ Por seguridad, este enlace expirará en 2 horas.</em><br>
                                    Si el botón no funciona, copia y pega esta URL en tu navegador:
                                </p>
                                <p style="color: #007bff; font-size: 12px; word-break: break-all; margin-bottom: 20px;">
                                    %s
                                </p>
                                <p style="color: #999999; font-size: 14px; margin-top: 20px;">
                                    Atentamente,<br><strong>El equipo de JyPS</strong>
                                </p>
                            </div>
                    
                        </div>
                    </div>
                    """.formatted(nombre, deepLink, deepLink);

            helper.setText(contenidoHtml, true); // El 'true' le dice que es formato HTML

            // ¡Enviamos el correo!
            mailSender.send(mensaje);

        } catch (MessagingException e) {
            // Aquí atraparíamos el error si falla (ej. sin internet o credenciales malas)
            System.err.println("Error al enviar el correo a " + destinatario);
            e.printStackTrace();
        }
    }

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String nombre, String deepLink) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject("🔑 Recuperación de contraseña - JyPS");

            String contenidoHtml = """
                    <div style="background-color: #f4f4f7; padding: 40px 20px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; text-align: center;">
                        <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 40px; border-radius: 8px; border: 1px solid #e1e1e1; box-shadow: 0 4px 10px rgba(0,0,0,0.05);">
                    
                            <h1 style="color: #f39c12; margin-top: 0; margin-bottom: 20px; font-size: 28px; letter-spacing: 1px;">
                                JyPS
                            </h1>
                    
                            <h2 style="color: #2c3e50; font-size: 22px; margin-bottom: 15px;">Hola, %s 🛡️</h2>
                    
                            <p style="color: #555555; font-size: 16px; line-height: 1.6; margin-bottom: 20px;">
                                Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en la plataforma JyPS.
                            </p>
                            <p style="color: #555555; font-size: 16px; line-height: 1.6; margin-bottom: 30px;">
                                Para continuar con el proceso, haz clic en el botón de abajo:
                            </p>
                    
                            <a href="%s" style="background-color: #f39c12; color: #ffffff; padding: 14px 30px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px; display: inline-block;">
                                Restablecer Contraseña
                            </a>
                    
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #eeeeee;">
                                <p style="color: #888888; font-size: 13px; line-height: 1.5; margin-bottom: 10px;">
                                    <em>⚠️ Este enlace es de un solo uso y expirará en 2 horas.</em><br>
                                    Si el botón no funciona, copia y pega esta URL en tu navegador:
                                </p>
                                <p style="color: #f39c12; font-size: 12px; word-break: break-all; margin-bottom: 20px;">
                                    %s
                                </p>
                                <hr style="border: 0; border-top: 1px solid #f4f4f4; margin: 20px 0;">
                                <p style="color: #e74c3c; font-size: 13px; font-weight: bold;">
                                    ¿No solicitaste este cambio?
                                </p>
                                <p style="color: #999999; font-size: 12px; line-height: 1.5;">
                                    Si tú no pediste restablecer tu contraseña, puedes ignorar este correo de forma segura. Tu cuenta seguirá protegida.
                                </p>
                            </div>
                    
                        </div>
                    </div>
                    """.formatted(nombre, deepLink, deepLink);

            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo de recuperación a " + destinatario);
            e.printStackTrace();
        }
    }

}
