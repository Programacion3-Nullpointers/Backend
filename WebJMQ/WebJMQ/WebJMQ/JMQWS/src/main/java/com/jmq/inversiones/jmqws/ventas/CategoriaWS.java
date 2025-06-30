
package com.jmq.inversiones.jmqws.ventas;

import com.jmq.inversiones.business.ventas.CategoriaService;
import com.jmq.inversiones.business.impl.ventas.CategoriaServiceImpl;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.pagos.Descuento;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "CategoriaWS")
public class CategoriaWS {

    private final CategoriaService categoriaWS;
    
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
    
    @WebMethod(operationName = "EliminarCategoria")
    public void eliminarCategoria(int id){
        try{
            categoriaWS.eliminarCategoria(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al eliminar la categoria "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ObtenerCategoria")
    public Categoria buscarCategoria(int id){
        try{
            return categoriaWS.buscarCategoria(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al eliminar la categoria "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarCategorias")
    public List<Categoria> listarCategorias(){
        try{
            return categoriaWS.listarCategorias();
        }
        catch (Exception ex){
            throw new WebServiceException("Error al eliminar la categoria "+ex.getMessage());
        }
    }
}
