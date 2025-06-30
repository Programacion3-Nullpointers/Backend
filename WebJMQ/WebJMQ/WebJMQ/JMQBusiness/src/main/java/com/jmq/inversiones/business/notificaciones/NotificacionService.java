package com.jmq.inversiones.business.notificaciones;

import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import java.util.List;

public interface NotificacionService {
    void registrarNotificacion(Notificacion n) throws Exception;
    void actualizarNotificacion(Notificacion n) throws Exception;
    void enviarNotificacion(Notificacion n) throws Exception;
    void eliminarNotificacion(int id) throws Exception;
    Notificacion buscarNotificacion(int id) throws Exception;
    List<Notificacion> listarNotificaciones() throws Exception;
    //List<Notificacion> listarPorEstado(String estado) throws Exception;
    //List<Notificacion> listarPorTipo(String tipo) throws Exception;
    
}
