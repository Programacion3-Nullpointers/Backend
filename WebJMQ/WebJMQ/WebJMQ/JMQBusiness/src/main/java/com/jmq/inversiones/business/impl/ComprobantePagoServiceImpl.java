package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.ComprobantePagoService;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.jmqpersistencia.dao.ComprobantePagoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ComprobantePagoDAOImpl;
import java.util.Date;
import java.util.List;

public class ComprobantePagoServiceImpl implements ComprobantePagoService{

    private final ComprobantePagoDAO comprobanteDAO;

    public ComprobantePagoServiceImpl() {
        this.comprobanteDAO = new ComprobantePagoDAOImpl();
    }

    @Override
    public void registrarComprobante(ComprobantePago comprobante) throws Exception {
        try {
            validarComprobante(comprobante);
            
            // Establecer fecha de pago si no esta definida
            if (comprobante.getFecha_pago() == null) {
                comprobante.setFecha_pago(new Date());
            }
            
            comprobanteDAO.agregar(comprobante);
        } catch (Exception e) {
            throw new Exception("Error al registrar comprobante: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarComprobante(ComprobantePago comprobante) throws Exception {
        try {
            if (comprobante.getId() <= 0) {
                throw new Exception("ID de comprobante inválido");
            }
            validarComprobante(comprobante);
            if (comprobanteDAO.obtener(comprobante.getId()) == null) {
                throw new Exception("Comprobante no encontrado");
            }
            
            comprobanteDAO.actualizar(comprobante);
        } catch (Exception e) {
            throw new Exception("Error al actualizar comprobante: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarComprobante(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de comprobante inválido");
            }
            
            comprobanteDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar comprobante: " + e.getMessage(), e);
        }
    }

    @Override
    public ComprobantePago buscarComprobante(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de comprobante inválido");
            }
            
            ComprobantePago comprobante = comprobanteDAO.obtener(id);
            if (comprobante == null) {
                throw new Exception("Comprobante no encontrado");
            }
            
            return comprobante;
        } catch (Exception e) {
            throw new Exception("Error al buscar comprobante: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ComprobantePago> listarComprobantes() throws Exception {
        try {
            return comprobanteDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar comprobantes: " + e.getMessage(), e);
        }
    }

    private void validarComprobante(ComprobantePago comprobante) throws Exception {
        if (comprobante.getOrden() == null) {
            throw new Exception("La orden de venta es requerida");
        }
        
        if (comprobante.getMetodoPago() == null) {
            throw new Exception("El método de pago es requerido");
        }
        
        if (comprobante.getMonto_total() <= 0) {
            throw new Exception("El monto total debe ser mayor a cero");
        }
    }
    
}
