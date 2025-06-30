package com.jmq.inversiones.business.impl.cotizaciones;

import com.jmq.inversiones.business.cotizaciones.CotizacionService;
import com.jmq.inversiones.dominio.cotizaciones.Cotizacion;
import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.cotizaciones.CotizacionDAO;
import com.jmq.inversiones.jmqpersistencia.dao.cotizaciones.ProductoCotizacionDAO;
import java.util.List;

public class CotizacionServiceImpl implements CotizacionService {

    private final CotizacionDAO cotizacionDAO;
    private final ProductoCotizacionDAO productoCotizacionDAO;
    public CotizacionServiceImpl(CotizacionDAO cotizacionDAO, ProductoCotizacionDAO productoCotizacionDAO) {
        this.cotizacionDAO = cotizacionDAO;
        this.productoCotizacionDAO = productoCotizacionDAO;
    }

    @Override
    public void registrarCotizacion(Cotizacion cotizacion) throws Exception {
        try {
            validarCotizacion(cotizacion);

            if (cotizacion.getProductos() == null || cotizacion.getProductos().isEmpty()) {
                throw new Exception("La cotización debe tener al menos un producto");
            }

            // Establecer estado por defecto
            if (cotizacion.getEstadoCotizacion() == null || cotizacion.getEstadoCotizacion().isEmpty()) {
                cotizacion.setEstadoCotizacion("PENDIENTE");
            }

            // Primero registrar la cotización (esto le asigna un ID)
            cotizacionDAO.agregar(cotizacion);

            // Luego registrar cada producto asociado
            for (ProductoCotizacion producto : cotizacion.getProductos()) {
                producto.setFid_cotizacion(cotizacion.getId()); // Setear el ID recién generado
                productoCotizacionDAO.agregar(producto);       // Insertar producto
            }

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
    
    @Override
    public List<Cotizacion> obtenerCotizacionesPorUsuario(int idUsuario) throws Exception {
        try {
            if (idUsuario <= 0) throw new Exception("ID de usuario inválido");
            return cotizacionDAO.obtenerPorUsuario(idUsuario);
        } catch (Exception e) {
            throw new Exception("Error al obtener cotizaciones del usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarEstado(int id, String estado) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de cotización inválido");
            }

            if (estado == null || estado.trim().isEmpty()) {
                throw new Exception("Estado de cotización no puede ser vacío");
            }

            Cotizacion cotizacion = cotizacionDAO.obtener(id);
            if (cotizacion == null) {
                throw new Exception("No existe una cotización con el ID especificado");
            }

            cotizacionDAO.actualizarEstado(id, estado);
            //return "BIEN";
        } catch (Exception e) {
            throw new Exception("Error al actualizar estado de cotización: " + e.getMessage(), e);
        }
    }

}
