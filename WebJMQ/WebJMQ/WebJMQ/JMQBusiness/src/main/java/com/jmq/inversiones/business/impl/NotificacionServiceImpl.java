package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.business.NotificacionService;

public class NotificacionServiceImpl implements NotificacionService {

    private final EmailService emailService = new EmailServiceImpl();

    @Override
    public void notificarEstadoPedido(String correo, String nombreCliente, String estado) {
        String asunto = "Estado de tu pedido actualizado";
        String cuerpo = "Hola " + nombreCliente + ",\n\n"
                + "Tu pedido ha cambiado de estado y ahora está marcado como: " + estado + ".\n\n"
                + "Gracias por tu compra.\nEquipo JMQ";
        enviar(correo, asunto, cuerpo);
    }

    @Override
    public void notificarEntrega(String correo, String nombreCliente) {
        String asunto = "Tu pedido ha sido entregado";
        String cuerpo = "Hola " + nombreCliente + ",\n\n"
                + "¡Confirmamos que tu pedido ha sido entregado correctamente!\n"
                + "Esperamos que lo disfrutes. Gracias por confiar en nosotros.\n\n"
                + "Saludos,\nEquipo JMQ";
        enviar(correo, asunto, cuerpo);
    }

    @Override
    public void notificarPromocion(String correo, String tituloPromo, String descripcion) {
        String asunto = "Nueva promoción: " + tituloPromo;
        String cuerpo = "¡Hola!\n\nTenemos una promoción para vos:\n\n"
                + descripcion + "\n\nAprovechala antes de que termine.\nEquipo JMQ";
        enviar(correo, asunto, cuerpo);
    }

    private void enviar(String correo, String asunto, String cuerpo) {
        try {
            emailService.enviarEmail(correo, asunto, cuerpo);
        } catch (Exception e) {
            System.err.println("Error al enviar notificación: " + e.getMessage());
        }
    }
}
