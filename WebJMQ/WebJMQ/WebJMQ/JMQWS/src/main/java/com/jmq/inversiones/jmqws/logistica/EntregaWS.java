/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.jmqws.logistica;

import com.jmq.inversiones.business.logistica.EntregaService;
import com.jmq.inversiones.business.impl.logistica.EntregaServiceImpl;
import com.jmq.inversiones.dominio.logistica.Entrega;
import com.jmq.inversiones.jmqpersistencia.daoimpl.logistica.EntregaDAOImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

@WebService(serviceName = "EntregaWS")
public class EntregaWS {

    private final EntregaService entregaWS;
    
    public EntregaWS(){
        entregaWS= new EntregaServiceImpl(new EntregaDAOImpl());
    }
    
    @WebMethod(operationName = "RegistrarEntrega")
    public void registrarEntrega(Entrega ent){
        try {
            entregaWS.registrarEntrega(ent);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Entrega "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ActualizarEntrega")
    public void ActualizarEntrega(Entrega ent){
        try {
            entregaWS.actualizarEntrega(ent);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar Entrega"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "EliminarEntrega")
    public void EliminarEntrega(int id){
        try {
            entregaWS.eliminarEntrega(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Entrega "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "BuscarEntrega")
    public Entrega buscarEntrega(int id){
        try {
            return entregaWS.buscarEntrega(id);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al buscar Entregas "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarEntregas")
    public List<Entrega> listarEntregas(){
        try {
            return entregaWS.listarEntrega();
        }
        catch (Exception ex){
            throw new WebServiceException("Error al leer Entregas "+ex.getMessage());
        }
    }
}
