
package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.pagos.FacturaService;
import com.jmq.inversiones.business.impl.pagos.FacturaServiceImpl;
import com.jmq.inversiones.dominio.pagos.Factura;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "FacturaWS")
public class FacturaWS {

    private FacturaService facturaWS;
    
    public FacturaWS(){
        facturaWS = new FacturaServiceImpl();
    }
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "RegistrarFactura")
    public void registrarFactura(Factura fac){
        try {
            facturaWS.registrarFactura(fac);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar factura "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ActualizarFactura")
    public void actualizarFactura(Factura fac){
        try {
            facturaWS.actualizarFactura(fac);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar factura "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "EliminarFactura")
    public void EliminarFactura(int id){
        try {
            facturaWS.eliminarFactura(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al eliminar factura "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarFacturas")
    public List<Factura> listarFacturas() throws Exception{
        try {
            return facturaWS.listarFacturas();
        }
        catch(Exception ex){
            throw new Exception("Error en el WS no se pudo listar facturas");
        }
    }
    
    @WebMethod(operationName = "buscarFactura")
    public Factura buscarFactura(int id) {
        try {
            return facturaWS.buscarFactura(id);
        }
        catch(Exception ex){
            throw new WebServiceException("Error en el WS no se pudo obtener la factura"+ ex.getMessage());
        }
    }
    
}
