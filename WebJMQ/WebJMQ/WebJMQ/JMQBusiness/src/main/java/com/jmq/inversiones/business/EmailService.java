package com.jmq.inversiones.business;

import java.util.List;

public interface EmailService {
    void enviarEmail(String destinatario, String asunto, String contenido) throws Exception;
    void enviarEmailMasivo(List<String> destinatarios, String asunto, String contenido) throws Exception;
}
