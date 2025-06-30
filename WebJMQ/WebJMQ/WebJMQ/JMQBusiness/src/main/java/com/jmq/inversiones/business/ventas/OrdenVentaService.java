package com.jmq.inversiones.business.ventas;

import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrdenVentaService {
    void registrarOrdenVenta(OrdenVenta boleta) throws Exception;
    void actualizarOrdenVenta(OrdenVenta boleta) throws Exception;
    void eliminarOrdenVenta(int id) throws Exception;
    OrdenVenta buscarOrdenVenta(int id) throws Exception;
    List<OrdenVenta> listarOrdenVentas() throws Exception;
    void actualizarEstado(int id, String estado) throws Exception;
    void agregarDetalle(OrdenVenta ordenVenta, Detalle detalle) throws Exception;
    void eliminarDetalle(OrdenVenta ordenVenta, Detalle detalle) throws Exception;
    List<OrdenVenta> listarOrdenVentaByUsuario(int id_usuario) throws Exception;
    List<OrdenVenta> filtrarOrdenesVenta(String estadoCompra, Boolean activo, Integer idUsuario,
                                          String fechaDesde, String fechaHasta) throws SQLException;
}
