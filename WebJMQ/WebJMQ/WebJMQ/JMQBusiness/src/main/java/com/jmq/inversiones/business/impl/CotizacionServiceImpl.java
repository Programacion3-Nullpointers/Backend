package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.CotizacionService;
import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.CotizacionDAO;
import java.util.List;

public class CotizacionServiceImpl implements CotizacionService {

    private final CotizacionDAO cotizacionDAO;

    public CotizacionServiceImpl(CotizacionDAO cotizacionDAO) {
        this.cotizacionDAO = cotizacionDAO;
    }

    @Override
    public void registrarCotizacion(Cotizacion cotizacion) throws Exception {
        try {
            validarCotizacion(cotizacion);
            
            if (cotizacion.getProductos() == null || cotizacion.getProductos().isEmpty()) {
                throw new Exception("La cotización debe tener al menos un producto");
            }
            
            if (cotizacion.getEstadoCotizacion() == null || cotizacion.getEstadoCotizacion().isEmpty()) {
                cotizacion.setEstadoCotizacion("PENDIENTE");
            }
            
            cotizacionDAO.agregar(cotizacion);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar cotización: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarCotizacion(Cotizacion cotizacion) throws Exception {
        try {
            if (cotizacion.getId() <= 0) {
                throw new Exception("ID de cotización inválido");
            }
            
            validarCotizacion(cotizacion);
            
            if (cotizacionDAO.obtener(cotizacion.getId()) == null) {
                throw new Exception("Cotización no encontrada");
            }
            
            cotizacionDAO.actualizar(cotizacion);
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar cotización: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarCotizacion(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de cotización inválido");
            }
            
            Cotizacion cotizacion = cotizacionDAO.obtener(id);
            if (cotizacion != null && "APROBADA".equals(cotizacion.getEstadoCotizacion())) {
                throw new Exception("No se puede eliminar una cotización aprobada");
            }
            
            cotizacionDAO.eliminar(id);
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar cotización: " + e.getMessage(), e);
        }
    }

    @Override
    public Cotizacion buscarCotizacion(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de cotización inválido");
            }
            
            Cotizacion cotizacion = cotizacionDAO.obtener(id);
            if (cotizacion == null) {
                throw new Exception("Cotización no encontrada");
            }
            
            return cotizacion;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar cotización: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cotizacion> listarCotizaciones() throws Exception {
        try {
            return cotizacionDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar cotizaciones: " + e.getMessage(), e);
        }
    }

    private void validarCotizacion(Cotizacion cotizacion) throws Exception {
        if (cotizacion.getUsuario() == null) {
            throw new Exception("El usuario es requerido");
        }
        
        if (cotizacion.getEstadoCotizacion() == null || cotizacion.getEstadoCotizacion().trim().isEmpty()) {
            throw new Exception("El estado de la cotización es requerido");
        }
    }
    
}
