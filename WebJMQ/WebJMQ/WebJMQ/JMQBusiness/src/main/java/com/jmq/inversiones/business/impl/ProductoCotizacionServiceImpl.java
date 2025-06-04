
package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.ProductoCotizacionService;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoCotizacionDAOImpl;
import java.util.List;


public class ProductoCotizacionServiceImpl implements ProductoCotizacionService{

    private final ProductoCotizacionDAO producto;
    
    public ProductoCotizacionServiceImpl(){
        producto = new ProductoCotizacionDAOImpl();
    }
    
    @Override
    public void registrarProductoCotizacion(ProductoCotizacion prod) throws Exception {
        try {
            if(prod.getCantidad()<1){
                throw new Exception("Debe ingresar una cantidad requerida");
            }
            if(prod.getDescripcion() == null || prod.getDescripcion().isEmpty()){
                throw new Exception("Debe ingresar una descripcion");
            }
            
            //Llama al DAO
            producto.agregar(prod);
        }
        catch (Exception ex){
            throw new Exception("Error al registrar el producto de cotizacion"+ex.getMessage());
        }
    }

    @Override
    public void actualizarProductoCotizacion(ProductoCotizacion prod) throws Exception {
        try {
            if(prod.getCantidad()<1){
                throw new Exception("Debe ingresar una cantidad requerida");
            }
            if(prod.getDescripcion() == null || prod.getDescripcion().isEmpty()){
                throw new Exception("Debe ingresar una descripcion");
            }
            
            producto.actualizar(prod);
            
        }
        catch(Exception ex){
            throw new Exception("Error al actualizar el producto"+ex.getMessage());
        }
    }

    @Override
    public void eliminarProductoCotizacion(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductoCotizacion buscarProductoCotizacion(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoCotizacion> listarProductoCotizacion() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoCotizacion> listarProductPorCotizacion(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarPrecioCotizacion(ProductoCotizacion prod, double precio) throws Exception {
        try{
            if(prod.getCantidad()<1){
                throw new Exception("Debe ingresar una cantidad requerida");
            }
            if(prod.getDescripcion() == null || prod.getDescripcion().isEmpty()){
                throw new Exception("Debe ingresar una descripcion");
            }
            if(precio<=0){
                throw new Exception("Debe ingresar una precio");
            }
            
            producto.actualizarPrecioCotizacion(prod, precio);
        }
        catch(Exception ex){
            throw new Exception("Error al actualizar el precio"+ex.getMessage());
        }
    }
    
}
