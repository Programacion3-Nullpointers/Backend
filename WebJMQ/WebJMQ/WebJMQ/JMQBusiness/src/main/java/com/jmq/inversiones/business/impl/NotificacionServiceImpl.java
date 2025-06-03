package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.business.NotificacionService;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.dao.NotificacionDAO;
import java.util.Date;
import java.util.List;

public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionDAO notificacionDAO;
    private final EmailService emailService; // Servicio para enviar emails

    public NotificacionServiceImpl(NotificacionDAO notificacionDAO, EmailService emailService) {
        if (notificacionDAO == null || emailService == null) {
            throw new IllegalArgumentException("DAO y EmailService no pueden ser null");
        }
        this.notificacionDAO = notificacionDAO;
        this.emailService = emailService;
    }

    @Override
    public void registrarNotificacion(Notificacion notificacion) throws Exception {
        try {
            validarNotificacion(notificacion);
            
            // Establecer valores por defecto
            notificacion.setFecha_envio(new Date());
            notificacion.setEstado("PENDIENTE");
            
            notificacionDAO.agregar(notificacion);
        } catch (Exception e) {
            throw new Exception("Error al registrar notificación: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarNotificacion(Notificacion notificacion) throws Exception {
        try {
            if (notificacion.getId() <= 0) {
                throw new Exception("ID de notificación inválido");
            }
            validarNotificacion(notificacion);
            
            notificacionDAO.actualizar(notificacion);
        } catch (Exception e) {
            throw new Exception("Error al actualizar notificación: " + e.getMessage(), e);
        }
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) throws Exception {
        try {
            validarNotificacion(notificacion);
            
            // Envío por email
            emailService.enviarEmail(
                notificacion.getDestinatario(), 
                notificacion.getAsunto(),      
                notificacion.getMensaje()
            );
            
            // Actualizar estado
            notificacion.setEstado("ENVIADA");
            notificacion.setFecha_envio(new Date());
            
            if (notificacion.getId() > 0) {
                notificacionDAO.actualizar(notificacion);
            } else {
                notificacionDAO.agregar(notificacion);
            }
            
        } catch (Exception e) {
            notificacion.setEstado("FALLIDA");
            if (notificacion.getId() > 0) {
                notificacionDAO.actualizar(notificacion);
            }
            throw new Exception("Error al enviar notificación por email: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarNotificacion(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de notificación inválido");
            }
            notificacionDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar notificación: " + e.getMessage(), e);
        }
    }

    @Override
    public Notificacion buscarNotificacion(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de notificación inválido");
            }
            return notificacionDAO.obtener(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar notificación: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Notificacion> listarNotificaciones() throws Exception {
        try {
            return notificacionDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar notificaciones: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para validaciones comunes
    private void validarNotificacion(Notificacion notificacion) throws Exception {
        if (notificacion.getTipo() == null || notificacion.getTipo().trim().isEmpty()) {
            throw new Exception("El tipo de notificación es obligatorio");
        }
        
        if (notificacion.getTipo() == null || !notificacion.getTipo().equalsIgnoreCase("EMAIL")) {
            throw new Exception("Solo se admiten notificaciones por EMAIL");
        }
        if (notificacion.getMensaje() == null || notificacion.getMensaje().isEmpty()) {
            throw new Exception("El mensaje es requerido");
        }
        
        if (notificacion.getDestinatario() == null || notificacion.getDestinatario().trim().isEmpty()) {
            throw new Exception("El destinatario es obligatorio");
        }
        if (notificacion.getAsunto() == null || notificacion.getAsunto().trim().isEmpty()) {
            throw new Exception("El asunto es obligatorio");
        }
    }

}
