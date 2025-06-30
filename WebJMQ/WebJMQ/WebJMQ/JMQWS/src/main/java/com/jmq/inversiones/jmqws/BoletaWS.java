
package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.pagos.BoletaService;
import com.jmq.inversiones.business.impl.pagos.BoletaServiceImpl;
import com.jmq.inversiones.dominio.pagos.Boleta;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "BoletaWS")
public class BoletaWS {

    private BoletaService boletaWS;
    
    public BoletaWS(){
        boletaWS = new BoletaServiceImpl();
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "registrarBoleta")
    public void registrarBoleta(Boleta bo){
        try {
            boletaWS.registrarBoleta(bo);
        }
        catch(Exception ex){
            throw new WebServiceException("Error en registro de la boleta "+ex.getMessage());
        }
        
    }
    
    @WebMethod(operationName = "actualizarBoleta")
    public void actualizarBoleta(Boleta bo){
        try {
            boletaWS.actualizarBoleta(bo);
        }
        catch(Exception ex){
            throw new WebServiceException("Error en la actualizacion de la boleta "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "buscarBoleta")
    public Boleta buscarBoleta(int id){
        try {
            return boletaWS.buscarBoleta(id);
        }
        catch(Exception ex){
            throw new WebServiceException("Error en la busqueda de la boleta "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarBoleta")
    public void eliminarBoleta(int id){
        try {
            boletaWS.eliminarBoleta(id);
        }
        catch(Exception ex){
            throw new WebServiceException("Error al eliminar la boleta "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarBoletas")
    public List<Boleta> listarBoleta(){
        try {
            return boletaWS.listarBoletas();
        }
        catch(Exception ex){
            throw new WebServiceException("Error en el listado de boletas "+ex.getMessage());
        }
    }
    
}
