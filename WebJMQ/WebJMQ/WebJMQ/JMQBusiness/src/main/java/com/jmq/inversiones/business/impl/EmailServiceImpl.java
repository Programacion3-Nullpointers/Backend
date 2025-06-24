package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.dominio.usuario.Usuario;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.List;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private final String remitente = "daylicamfer123@gmail.com";
    private final String contrasena = "ljubmddhvmntxnhb"; // Contraseña de aplicación

    private Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        
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
    public void enviarRecuperacionPassword(String correo, String nombre, String enlaceRecuperacion) {
        try {
            String contenido = "<html><body>"
                    + "<h3>Hola " + nombre + ",</h3>"
                    + "<p>Has solicitado restablecer tu contraseña.</p>"
                    + "<p><a href='" + enlaceRecuperacion + "'>Haz clic aquí para restablecerla</a></p>"
                    + "<p>Este enlace expirará en 15 minutos.</p>"
                    + "<br><p>Saludos,<br>Equipo JMQ</p>"
                    + "</body></html>";

            enviarEmailHtml(correo, "Recuperación de contraseña", contenido);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    private void enviarEmailHtml(String destinatario, String asunto, String contenidoHtml) throws Exception {
        Session session = crearSesion();

        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensaje.setSubject(asunto);

        MimeBodyPart cuerpoHtml = new MimeBodyPart();
        cuerpoHtml.setContent(contenidoHtml, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(cuerpoHtml);

        mensaje.setContent(multipart);

        Transport.send(mensaje);
        System.out.println("Correo HTML enviado a: " + destinatario);
    }
}
