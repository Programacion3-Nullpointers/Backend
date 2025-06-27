/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.DetalleService;
import com.jmq.inversiones.business.impl.DetalleServiceImpl;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.jmqpersistencia.daoimpl.DetalleDAOImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

@WebService(serviceName = "DetalleWS")
public class DetalleWS {

    private final DetalleService detalleWS;
    
    public DetalleWS(){
        detalleWS= new DetalleServiceImpl(new DetalleDAOImpl());
    }
    
    @WebMethod(operationName = "RegistrarDetalle")
    public void registrarDetalle(Detalle det){
        try {
            detalleWS.registrarDetalle(det);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Detalle "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ActualizarDetalle")
    public void ActualizarDetalle(Detalle det){
        try {
            detalleWS.actualizarDetalle(det);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al actualizar Detalle"+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "EliminarDetalle")
    public void EliminarDetalle(int idOrden, int idProducto){
        try {
            detalleWS.eliminarDetalle(idOrden,idProducto);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al registrar Detalle "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "BuscarDetalle")
    public Detalle buscarDetalle(int idOrden, int idProducto){
        try {
            return detalleWS.buscarDetalle(idOrden,idProducto);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al buscar Detalles "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarDetalles")
    public List<Detalle> listarDetalles(){
        try {
            return detalleWS.listarDetalles();
        }
        catch (Exception ex){
            throw new WebServiceException("Error al leer Detalles "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "ListarPorOrden")
    public List<Detalle> listarPorOrden(int idOrden){
        try {
            return detalleWS.listarPorOrden(idOrden);
        }
        catch (Exception ex){
            throw new WebServiceException("Error al leer Detalles por orden "+ex.getMessage());
        }
    }
    
}
