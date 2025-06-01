package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import java.util.List;

public interface ComprobantePagoService {
    void registrarComprobante(ComprobantePago categoria) throws Exception;
    void actualizarComprobante(ComprobantePago categoria) throws Exception;
    void eliminarComprobante(int id) throws Exception;
    ComprobantePago buscarComprobante(int id) throws Exception;
    List<ComprobantePago> listarComprobantes() throws Exception;

}
