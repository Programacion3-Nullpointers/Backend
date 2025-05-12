package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.List;

public interface OrdenVentaService {
    void registrarOrdenVenta(OrdenVenta boleta) throws Exception;
    void actualizarOrdenVenta(OrdenVenta boleta) throws Exception;
    void eliminarOrdenVenta(int id) throws Exception;
    OrdenVenta buscarOrdenVenta(int id) throws Exception;
    List<OrdenVenta> listarOrdenVentas() throws Exception;
    void actualizarEstado(int id, String estado) throws Exception;
      
}
