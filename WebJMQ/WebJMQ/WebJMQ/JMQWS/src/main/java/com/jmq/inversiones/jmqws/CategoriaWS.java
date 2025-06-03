
package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.CategoriaService;
import com.jmq.inversiones.business.impl.CategoriaServiceImpl;
import com.jmq.inversiones.dominio.ventas.Categoria;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;


@WebService(serviceName = "CategoriaWS")
public class CategoriaWS {

    private CategoriaService categoriaWS;
    
    public CategoriaWS(){
        categoriaWS = new CategoriaServiceImpl();
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "RegistrarCategoria")
    public void registrarCategoria(Categoria cat){
       try {
           categoriaWS.registrarCategoria(cat);
       } 
       catch (Exception ex){
           throw new WebServiceException("Error al registrar categoria"+ex.getMessage());
       }
    }
    
    @WebMethod(operationName = "ActualizarCategoria")
    public void actualizarCategoria(Categoria cat){
        try {
            categoriaWS.actualizarCategoria(cat);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar la categoria"+ex.getMessage());
        }
    }
}
