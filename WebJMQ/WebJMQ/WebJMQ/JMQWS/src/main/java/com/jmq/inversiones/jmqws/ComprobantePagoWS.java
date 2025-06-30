package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.ventas.ComprobantePagoService;
import com.jmq.inversiones.business.impl.ventas.ComprobantePagoServiceImpl;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.List;

@WebService(serviceName = "ComprobantePagoWS")
public class ComprobantePagoWS {

    private final ComprobantePagoService comprobanteService;

    public ComprobantePagoWS() {
        this.comprobanteService = new ComprobantePagoServiceImpl();
    }

    @WebMethod(operationName = "registrarComprobante")
    public void registrarComprobante(@WebParam(name = "comprobante") ComprobantePago cp) {
        try {
            comprobanteService.registrarComprobante(cp);
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar el comprobante: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "actualizarComprobante")
    public void actualizarComprobante(@WebParam(name = "comprobante") ComprobantePago cp) {
        try {
            comprobanteService.actualizarComprobante(cp);
        } catch (Exception e) {
            throw new WebServiceException("Error al actualizar el comprobante: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "eliminarComprobante")
    public void eliminarComprobante(@WebParam(name = "id") int id) {
        try {
            comprobanteService.eliminarComprobante(id);
        } catch (Exception e) {
            throw new WebServiceException("Error al eliminar el comprobante: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "buscarComprobante")
    public ComprobantePago buscarComprobante(@WebParam(name = "id") int id) {
        try {
            return comprobanteService.buscarComprobante(id);
        } catch (Exception e) {
            throw new WebServiceException("Error al buscar el comprobante: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarComprobantes")
    public List<ComprobantePago> listarComprobantes() {
        try {
            return comprobanteService.listarComprobantes();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar los comprobantes: " + e.getMessage(), e);
        }
    }
}
