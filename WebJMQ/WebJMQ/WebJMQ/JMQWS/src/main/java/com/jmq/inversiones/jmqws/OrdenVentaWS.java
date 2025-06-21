/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.jmqws;
import com.jmq.inversiones.business.*;
import com.jmq.inversiones.business.impl.*;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;


import com.jmq.inversiones.jmqpersistencia.daoimpl.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
/**
 *
 * @author LUIS
 */
@WebService(serviceName = "OrdenVentaWS")
public class OrdenVentaWS {
    private final OrdenVentaService ordenVentaService;
    
    public OrdenVentaWS(){
        this.ordenVentaService = new OrdenVentaServiceImpl(new OrdenVentaDAOImpl(),new DetalleDAOImpl());   
    }
    @WebMethod
    public OrdenVenta registrarOrdenVentaService(@WebParam(name = "ordenVenta") OrdenVenta ordenVenta) throws Exception{
        ordenVentaService.registrarOrdenVenta(ordenVenta);
        return ordenVentaService.buscarOrdenVenta(ordenVenta.getId());
    }
    @WebMethod
    public void actualizarOrdenVentaService(@WebParam(name = "ordenVenta") OrdenVenta ordenVenta) throws Exception{
        ordenVentaService.actualizarOrdenVenta(ordenVenta);
    }
    @WebMethod
    public void actualizarEstadoOrdenVentaService(
        @WebParam(name = "id") int id,
        @WebParam(name = "estado") String estado
    ) throws Exception {
        ordenVentaService.actualizarEstado(id, estado);
    }
    @WebMethod
    public void eliminarOrdenVentaService(@WebParam(name = "id") int id) throws Exception{
        ordenVentaService.eliminarOrdenVenta(id);
    }
    @WebMethod
    public OrdenVenta buscarOrdenVentaServicesById(@WebParam(name = "id") int id) throws Exception{
        return ordenVentaService.buscarOrdenVenta(id);
    }
    @WebMethod
    public List<OrdenVenta> listarOrdenVenta() throws Exception{
        return ordenVentaService.listarOrdenVentas();
    } 
   @WebMethod(operationName = "agregarDetalleAOrdenVenta")
    public void agregarDetalleOrdenVentaService(@WebParam(name = "ordenVenta") OrdenVenta orden,
            @WebParam(name = "detalle") Detalle detalle) throws Exception{
        ordenVentaService.agregarDetalle(orden, detalle);
    }
    @WebMethod(operationName = "eliminarDetalleDeOrdenVenta")
    public void eliminarDetalleOrdenVentaService(@WebParam(name = "ordenVenta") OrdenVenta orden,
            @WebParam(name = "detalle") Detalle detalle) throws Exception{
        ordenVentaService.eliminarDetalle(orden, detalle);
    }
    @WebMethod(operationName = "obtenerOrdenesVentasPorUsuario")
    public List<OrdenVenta> obtenerOrdenVentasByUsuario(@WebParam(name="id") int id) throws Exception{
        return ordenVentaService.listarOrdenVentaByUsuario(id);
    }
}
