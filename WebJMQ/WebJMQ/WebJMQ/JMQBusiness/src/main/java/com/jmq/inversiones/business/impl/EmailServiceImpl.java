package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
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
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
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
}
