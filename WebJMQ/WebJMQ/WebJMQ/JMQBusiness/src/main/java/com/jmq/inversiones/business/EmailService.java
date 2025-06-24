package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.usuario.Usuario;
import java.util.List;

public interface EmailService {
    void enviarEmail(String destinatario, String asunto, String contenido) throws Exception;
    void enviarEmailMasivo(List<String> destinatarios, String asunto, String contenido) throws Exception;
    void enviarRecuperacionPassword(String correo, String nombre, String enlaceRecuperacion);
}
