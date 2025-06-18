package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.dominio.usuario.Usuario;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.List;
import java.util.Properties;


public class EmailServiceImpl implements EmailService {

    private final String remitente = "daylicamfer123@gmail.com";
    private final String contrasena = "ljubmddhvmntxnhb"; // NO tu contraseña de Google normal

    private Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        props.put("mail.debug", "true");  // Agrega esto para ver los logs del correo

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasena);
            }
        });
    }


    @Override
    public void enviarEmail(String destinatario, String asunto, String contenido) throws Exception {
        Session session = crearSesion();

        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);

        Transport.send(mensaje);
        System.out.println("Correo enviado a: " + destinatario);
    }

    @Override
    public void enviarEmailMasivo(List<String> destinatarios, String asunto, String contenido) throws Exception {
        for (String destinatario : destinatarios) {
            enviarEmail(destinatario, asunto, contenido);
        }
    }

   @Override
    public void enviarRecuperacionPassword(Usuario usuario) {
        try {
            String link = "http://localhost:50463/Restablecer.aspx?token=" + usuario.getToken_reset();
            String contenido = "Hola " + usuario.getNombreUsuario() + ",\n\n"
                    + "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace:\n"
                    + link + "\n\n"
                    + "Este enlace expirará en 1 hora.\n\n"
                    + "Saludos,\nTu equipo JMQ";

            enviarEmail(usuario.getCorreo(), "Recuperación de contraseña", contenido);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

}   
