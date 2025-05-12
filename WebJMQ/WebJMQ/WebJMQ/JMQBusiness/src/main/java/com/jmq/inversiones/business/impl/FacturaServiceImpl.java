package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.FacturaService;
import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import java.util.List;

public class FacturaServiceImpl implements FacturaService{

    private final FacturaDAO facturaDAO;
    
    public FacturaServiceImpl(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    @Override
    public void registrarFactura(Factura factura) throws Exception {
        try {
            if (factura == null) {
                throw new Exception("La factura no puede ser nula");
            }
            if (factura.getRUC() == null || factura.getRUC().isEmpty()) {
                throw new Exception("El RUC es obligatorio");
            }
            if (factura.getRazon_social() == null || factura.getRazon_social().isEmpty()) {
                throw new Exception("La razón social es obligatoria");
            }
            
            facturaDAO.agregar(factura);
        } catch (Exception e) {
            throw new Exception("Error al registrar factura: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarFactura(Factura factura) throws Exception {
        try {
            if (factura == null) {
                throw new Exception("La factura no puede ser nula");
            }
            if (factura.getId() <= 0) {
                throw new Exception("ID de factura inválido");
            }
            
            facturaDAO.actualizar(factura);
        } catch (Exception e) {
            throw new Exception("Error al actualizar factura: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarFactura(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de factura inválido");
            }
            
            facturaDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar factura: " + e.getMessage(), e);
        }
    }

    @Override
    public Factura buscarFactura(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de factura inválido");
            }
            
            return facturaDAO.obtener(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar factura: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Factura> listarFacturas() throws Exception {
        try {
            return facturaDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar facturas: " + e.getMessage(), e);
        }
    }
    
}
