package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.pagos.Factura;
import java.util.List;

public interface FacturaService {
    void registrarFactura(Factura factura) throws Exception;
    void actualizarFactura(Factura factura) throws Exception;
    void eliminarFactura(int id) throws Exception;
    Factura buscarFactura(int id) throws Exception;
    List<Factura> listarFacturas() throws Exception;
    
}
