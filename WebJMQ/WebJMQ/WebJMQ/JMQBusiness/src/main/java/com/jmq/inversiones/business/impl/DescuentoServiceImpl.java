package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.DescuentoService;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;
import java.util.List;

public class DescuentoServiceImpl implements DescuentoService{

    private final DescuentoDAO descuentoDAO;

    public DescuentoServiceImpl(DescuentoDAO descuentoDAO) {
        this.descuentoDAO = descuentoDAO;
    }

    @Override
    public void registrarDescuento(Descuento descuento) throws Exception {
        try {
            validarDescuento(descuento);
            
            descuentoDAO.agregar(descuento);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar descuento: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarDescuento(Descuento descuento) throws Exception {
        try {
            if (descuento.getId() <= 0) {
                throw new Exception("ID de descuento inválido");
            }
            validarDescuento(descuento);
            if (descuentoDAO.obtener(descuento.getId()) == null) {
                throw new Exception("Descuento no encontrado");
            }
            
            descuentoDAO.actualizar(descuento);
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar descuento: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarDescuento(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de descuento inválido");
            }
            
            descuentoDAO.eliminar(id);
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar descuento: " + e.getMessage(), e);
        }
    }

    @Override
    public Descuento buscarDescuento(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de descuento inválido");
            }
            
            return descuentoDAO.obtener(id);
            
        } catch (Exception e) {
            throw new Exception("Error al buscar descuento: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Descuento> listarDescuentos() throws Exception {
        try {
            return descuentoDAO.listarTodos();
            
        } catch (Exception e) {
            throw new Exception("Error al listar descuentos: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para validaciones
    private void validarDescuento(Descuento descuento) throws Exception {
        if (descuento.getPorcentaje() <= 0 || descuento.getPorcentaje() > 100) {
            throw new Exception("El porcentaje de descuento debe estar entre 1 y 100");
        }
    }
}
