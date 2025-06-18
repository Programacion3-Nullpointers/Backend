package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.BoletaService;
import com.jmq.inversiones.dominio.pagos.Boleta;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.BoletaDAOImpl;
import java.util.Date;
import java.util.List;

public class BoletaServiceImpl implements BoletaService{

    private final BoletaDAO boletaDAO;

    public BoletaServiceImpl( ) {
        this.boletaDAO = new BoletaDAOImpl();
    }

    @Override
    public void registrarBoleta(Boleta boleta) throws Exception {
        try {
            validarBoleta(boleta);
            
            // Establecer fecha de emisión si no está definida
            if (boleta.getFecha_emision() == null) {
                boleta.setFecha_emision(new Date());
            }
            
            boletaDAO.agregar(boleta);
        } catch (Exception e) {
            throw new Exception("Error al registrar boleta: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarBoleta(Boleta boleta) throws Exception {
        try {
            if (boleta.getId() <= 0) {
                throw new Exception("ID de boleta inválido");
            }
            
            validarBoleta(boleta);
            
            Boleta existente = boletaDAO.obtener(boleta.getId());
            if (existente == null) {
                throw new Exception("Boleta no encontrada");
            }
            
            boletaDAO.actualizar(boleta);
        } catch (Exception e) {
            throw new Exception("Error al actualizar boleta: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarBoleta(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de boleta inválido");
            }
            
            boletaDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar boleta: " + e.getMessage(), e);
        }
    }

    @Override
    public Boleta buscarBoleta(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de boleta inválido");
            }
            
            Boleta boleta = boletaDAO.obtener(id);
            if (boleta == null) {
                throw new Exception("Boleta no encontrada");
            }
            
            return boleta;
        } catch (Exception e) {
            throw new Exception("Error al buscar boleta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Boleta> listarBoletas() throws Exception {
        try {
            return boletaDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar boletas: " + e.getMessage(), e);
        }
    }

    private void validarBoleta(Boleta boleta) throws Exception {
        if (boleta.getOrden() == null) {
            throw new Exception("La orden de venta es requerida");
        }
        
        if (boleta.getMetodoPago() == null) {
            boleta.setMetodoPago(MetodoPago.tarjeta);
        }
        
        if (boleta.getMonto_total() <= 0) {
            throw new Exception("El monto total debe ser mayor a cero");
        }
        if (boleta.getFecha_emision() == null)
            boleta.setFecha_emision(new Date());
        if (boleta.getFecha_pago() == null)
            boleta.setFecha_pago(new Date());
        if (boleta.getDni() == null || boleta.getDni().trim().isEmpty()) {
            throw new Exception("El DNI es requerido");
        }
        if (boleta.getNombre() == null || boleta.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es requerido");
        }
    }
    
}
