package com.jmq.inversiones.business.impl.pagos;

import com.jmq.inversiones.business.pagos.DescuentoService;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.dao.pagos.DescuentoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if (descuento.getNumDescuento()<= 0 || descuento.getNumDescuento()> 100) {
            throw new Exception("El porcentaje de descuento debe estar entre 1 y 100");
        }
    }
      @Override
    public void activarDescuento(int idDescuento) throws Exception{
        try {
            descuentoDAO.activarDescuento(idDescuento);
            
        } catch (Exception e) {
            throw new Exception("Error al Activar descuento: " + e.getMessage(), e);
        }
    }
    @Override
    public void desactivarDescuento(int idDescuento) throws Exception{
        try {
            descuentoDAO.desactivarDescuento(idDescuento);
            
        } catch (Exception e) {
            throw new Exception("Error al Activar descuento: " + e.getMessage(), e);
        }
    }
     @Override
    public List<Descuento> filtrarDescuentos(Boolean activo, Integer porcentajeMin, Integer porcentajeMax) throws SQLException{
       try {
            return descuentoDAO.filtrarDescuentos(activo, porcentajeMin, porcentajeMax);
            
        } catch (SQLException e) {
           try {
               throw new Exception("Error al filtrar descuentos: " + e.getMessage(), e);
           } catch (Exception ex) {
               Logger.getLogger(DescuentoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return null;
    }

}
