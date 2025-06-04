package com.jmq.inversiones.jmqws;



import com.jmq.inversiones.business.CotizacionService;
import com.jmq.inversiones.business.impl.CotizacionServiceImpl;
import com.jmq.inversiones.dominio.cotizaciones.Cotizacion;


import com.jmq.inversiones.jmqpersistencia.daoimpl.CotizacionDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoCotizacionDAOImpl;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.List;


@WebService(serviceName = "CotizacionWS")
public class CotizacionWS {

    private final CotizacionService cotizacionService;

    public CotizacionWS() {
        this.cotizacionService = new CotizacionServiceImpl(new CotizacionDAOImpl(), new ProductoCotizacionDAOImpl());
    }

    @WebMethod
    public void registrarCotizacion(@WebParam(name = "cotizacion") Cotizacion cotizacion) throws Exception {
        cotizacionService.registrarCotizacion(cotizacion);
    }

    @WebMethod
    public void actualizarCotizacion(@WebParam(name = "cotizacion") Cotizacion cotizacion) throws Exception {
        cotizacionService.actualizarCotizacion(cotizacion);
    }

    @WebMethod
    public void eliminarCotizacion(@WebParam(name = "id") int id) throws Exception {
        cotizacionService.eliminarCotizacion(id);
    }

    @WebMethod
    public Cotizacion buscarCotizacion(@WebParam(name = "id") int id) throws Exception {
        return cotizacionService.buscarCotizacion(id);
    }

    @WebMethod
    public List<Cotizacion> listarCotizaciones() throws Exception {
        return cotizacionService.listarCotizaciones();
    }
    
    @WebMethod(operationName = "obtenerCotizacionesPorUsuario")
    public List<Cotizacion> obtenerCotizacionesPorUsuario(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return cotizacionService.obtenerCotizacionesPorUsuario(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al obtener cotizaciones por usuario: " + e.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEstadoCotizacion")
    public void actualizarEstadoCotizacion(
        @WebParam(name = "id") int id,
        @WebParam(name = "estado") String estado
    ) {
        try {
            cotizacionService.actualizarEstado(id, estado);
            //return "ESTADO ACTUALIZADO";
        } catch (Exception e) {
            throw new WebServiceException("Error al actualizar estado: " + e.getMessage());
        }
    }

}
