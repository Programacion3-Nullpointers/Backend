package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EntregaService;
import com.jmq.inversiones.business.NotificacionService;
import com.jmq.inversiones.dominio.logistica.Entrega;
import com.jmq.inversiones.dominio.logistica.TipoEntrega;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.EntregaDAO;
import java.util.Date;
import java.util.List;

public class EntregaServiceImpl implements EntregaService{

    private final EntregaDAO entregaDAO;

    public EntregaServiceImpl(EntregaDAO entregaDAO) {
        this.entregaDAO = entregaDAO;
    }

    @Override
    public void registrarEntrega(Entrega entrega) throws Exception {
        try {
            // Validaciones básicas
            validarEntrega(entrega);
            
            // Establecer fecha de entrega si no está definida
            if (entrega.getFecha_entrega() == null) {
                entrega.setFecha_entrega(new Date()); // Fecha actual por defecto
            }
            
            // Llamar al DAO para insertar
            entregaDAO.agregar(entrega);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar entrega: " + entrega.toString(), e);
        }
    }

    @Override
    public void actualizarEntrega(Entrega entrega) throws Exception {
        try {
            // Validaciones
            if (entrega.getId() <= 0) {
                throw new Exception("ID de entrega inválido");
            }
            validarEntrega(entrega);
            
            // Verificar que la entrega exista
            Entrega existente = entregaDAO.obtener(entrega.getId());
            if (existente == null) {
                throw new Exception("Entrega no encontrada");
            }
            
            // Llamar al DAO para actualizar
            entregaDAO.actualizar(entrega);
            NotificacionService notificacionService = new NotificacionServiceImpl();
            Usuario usuario = entrega.getOrden() != null ? entrega.getOrden().getUsuario() : null;
            if (usuario != null) {
                notificacionService.notificarEntrega(usuario.getCorreo(), usuario.getNombreUsuario());
            }

        } catch (Exception e) {
            throw new Exception("Error al actualizar entrega: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarEntrega(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de entrega inválido");
            }
            
            // Llamar al DAO para eliminar
            entregaDAO.eliminar(id);
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar entrega: " + e.getMessage(), e);
        }
    }

    @Override
    public Entrega buscarEntrega(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de entrega inválido");
            }
            
            // Llamar al DAO para buscar
            return entregaDAO.obtener(id);
            
        } catch (Exception e) {
            throw new Exception("Error al buscar entrega: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Entrega> listarEntrega() throws Exception {
        try {
            // Llamar al DAO para listar todas
            return entregaDAO.listarTodos();
            
        } catch (Exception e) {
            throw new Exception("Error al listar entregas: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para validaciones comunes
    private void validarEntrega(Entrega entrega) throws Exception {
        if (entrega.getOrden() == null) {
            throw new Exception("La orden de venta es requerida");
        }
        if (entrega.getTipoEntrega() == null) {
            if (entrega.getDniRecibo() != null) entrega.setTipoEntrega(TipoEntrega.RECOJO);
            else entrega.setTipoEntrega(TipoEntrega.DELIVERY);
        }
        if (entrega.getTipoEntrega() == TipoEntrega.RECOJO && (entrega.getDniRecibo() == null || entrega.getDniRecibo().length()!=8)){
            throw new Exception("El DNI del receptor es inválido");
        }
        if (entrega.getTipoEntrega() == TipoEntrega.DELIVERY && (entrega.getDireccion() == null || entrega.getDireccion().trim().isEmpty())) {
            throw new Exception("La dirección de entrega es requerida");
        }
    }
}
