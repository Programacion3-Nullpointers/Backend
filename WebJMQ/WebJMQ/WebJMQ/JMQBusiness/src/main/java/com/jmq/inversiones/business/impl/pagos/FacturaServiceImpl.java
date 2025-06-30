package com.jmq.inversiones.business.impl.pagos;

import com.jmq.inversiones.business.pagos.FacturaService;
import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.jmqpersistencia.dao.pagos.FacturaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.pagos.FacturaDAOImpl;
import java.util.Date;
import java.util.List;

public class FacturaServiceImpl implements FacturaService{

    private final FacturaDAO facturaDAO;
    
    public FacturaServiceImpl() {
        this.facturaDAO = new FacturaDAOImpl();
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
            if (factura.getMetodoPago() == null) {
                factura.setMetodoPago(MetodoPago.tarjeta);
            }
            if (factura.getFecha_emision() == null)
                factura.setFecha_emision(new Date());
            if (factura.getFecha_pago() == null)
                factura.setFecha_pago(new Date());
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
