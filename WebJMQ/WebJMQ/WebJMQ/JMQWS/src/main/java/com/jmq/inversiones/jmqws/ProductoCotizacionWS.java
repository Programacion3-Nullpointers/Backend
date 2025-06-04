/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.ProductoCotizacionService;
import com.jmq.inversiones.business.impl.ProductoCotizacionServiceImpl;
import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

/**
 *
 * @author omen
 */
@WebService(serviceName = "ProductoCotizacionWS")
public class ProductoCotizacionWS {

    private final ProductoCotizacionService prod;
    
    public ProductoCotizacionWS(){
        prod = new ProductoCotizacionServiceImpl();
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "RegistrarPrecioProdCoti")
    public void registrarProductoCotizacion(ProductoCotizacion pr, double precio){
        try {
            prod.registrarProductoCotizacion(pr);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar el producto"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ActualizarProductoCotizacion")
    public void actualizarProductoCotizacion(ProductoCotizacion pr){
        try {
            prod.actualizarProductoCotizacion(pr);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar el producto"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "EliminarProductoCotizacion")
    public void eliminarProductoCotizacion(int id){
        try {
            prod.eliminarProductoCotizacion(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar el producto"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarProductoCotizacion")
    public List<ProductoCotizacion> listarProductoCotizacion(){
        try {
            return prod.listarProductoCotizacion();
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar el producto"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarProductosPorCotizacion")
    public List<ProductoCotizacion> listarProductosPorCotizacion(int id){
        try {
            return prod.listarProductPorCotizacion(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar el producto"+ex.getMessage());
        }
    }
    
    
    
    @WebMethod(operationName = "ActualizarPrecioProdCoti")
    public void actualizarPrecioProductoCotizacion(ProductoCotizacion pr, double precio){
        try {
            prod.actualizarPrecioCotizacion(pr, precio);
        }
        catch (Exception ex){
            throw new WebServiceException("Error actualizar el precio"+ex.getMessage());
        }
    }
}
