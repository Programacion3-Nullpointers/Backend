package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import java.util.List;

public interface NotificacionService {
     void notificarEstadoPedido(String correo, String nombreCliente, String estado);
    void notificarEntrega(String correo, String nombreCliente);
    void notificarPromocion(String correo, String tituloPromo, String descripcion);
//    void enviarNotificacion(Notificacion notificacion) throws Exception;

}
