
package com.jmq.inversiones.jmqws.pagos;

import com.jmq.inversiones.business.pagos.DescuentoService;
import com.jmq.inversiones.business.impl.pagos.DescuentoServiceImpl;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.daoimpl.pagos.DescuentoDAOImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.sql.SQLException;
import java.util.List;


@WebService(serviceName = "DescuentoWS")
public class DescuentoWS {

    private DescuentoService descuentoWS;
    
    public DescuentoWS(){
        descuentoWS= new DescuentoServiceImpl(new DescuentoDAOImpl());
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "RegistrarDescuento")
    public void registrarDescuento(Descuento desc){
        try {
            descuentoWS.registrarDescuento(desc);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Descuento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ActualizarDescuento")
    public void actualizarDescuento(Descuento desc){
        try {
            descuentoWS.actualizarDescuento(desc);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Descuento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "EliminarDescuento")
    public void eliminarDescuento(int id){
        try {
            descuentoWS.eliminarDescuento(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Descuento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "BuscarDescuento")
    public Descuento buscarDescuento(int id){
        try {
            return descuentoWS.buscarDescuento(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Descuento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarDescuentos")
    public List<Descuento> listarDescuentos(){
        try {
            return descuentoWS.listarDescuentos();
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Descuento "+ex.getMessage());
        }
    }
      @WebMethod(operationName = "activarDescuento")
    public void activarDescuento(int idDescuento){
        try {
           descuentoWS.activarDescuento(idDescuento);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al activar Descuento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "desactivarDescuento")
    public void desactivarDescuento(int idDescuento){
        try {
           descuentoWS.desactivarDescuento(idDescuento);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al activar Descuento "+ex.getMessage());
        }
    }
     @WebMethod(operationName = "filtrarDescuentos")
    public List<Descuento> filtrarDescuentos(Boolean activo, Integer porcentajeMin, Integer porcentajeMax){
        try {
           return descuentoWS.filtrarDescuentos(activo, porcentajeMin, porcentajeMax);
        }
        catch (SQLException ex){
            throw new WebServiceException("Error al filtrar Descuentos "+ex.getMessage());
        }
        
    }
    
}
