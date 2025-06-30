package com.jmq.inversiones.jmqws.notificacion;

import com.jmq.inversiones.business.notificaciones.NotificacionService;
import com.jmq.inversiones.business.impl.notificaciones.EmailServiceImpl;
import com.jmq.inversiones.business.impl.notificaciones.NotificacionServiceImpl;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.notificaciones.NotificacionDAOImpl;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.List;

@WebService(serviceName = "NotificacionWS")
public class NotificacionWS {

    private final NotificacionService servicio;

    public NotificacionWS() {
        this.servicio = new NotificacionServiceImpl(new NotificacionDAOImpl(), new EmailServiceImpl());
    }

    @WebMethod(operationName = "registrarNotificacion")
    public void registrarNotificacion(@WebParam(name = "notificacion") Notificacion n) {
        try {
            servicio.registrarNotificacion(n);
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar notificación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "actualizarNotificacion")
    public void actualizarNotificacion(@WebParam(name = "notificacion") Notificacion n) {
        try {
            servicio.actualizarNotificacion(n);
        } catch (Exception e) {
            throw new WebServiceException("Error al actualizar notificación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarNotificacion")
    public void eliminarNotificacion(@WebParam(name = "id") int id) {
        try {
            servicio.eliminarNotificacion(id);
        } catch (Exception e) {
            throw new WebServiceException("Error al eliminar notificación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "buscarNotificacion")
    public Notificacion buscarNotificacion(@WebParam(name = "id") int id) {
        try {
            return servicio.buscarNotificacion(id);
        } catch (Exception e) {
            throw new WebServiceException("Error al buscar notificación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarNotificaciones")
    public List<Notificacion> listarNotificaciones() {
        try {
            return servicio.listarNotificaciones();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar notificaciones: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "enviarNotificacion")
    public void enviarNotificacion(@WebParam(name = "notificacion") Notificacion n) {
        try {
            servicio.enviarNotificacion(n);
        } catch (Exception e) {
            throw new WebServiceException("Error al enviar notificación: " + e.getMessage());
        }
    }
}