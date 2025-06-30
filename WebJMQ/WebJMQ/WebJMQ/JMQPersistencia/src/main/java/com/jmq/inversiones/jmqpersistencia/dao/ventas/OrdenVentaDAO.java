package com.jmq.inversiones.jmqpersistencia.dao.ventas;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public interface OrdenVentaDAO extends BaseDAO<OrdenVenta>{
    List<OrdenVenta> listarPorUsuario(int idUsuario);
    List<OrdenVenta> filtrarOrdenesVenta(String estadoCompra, Boolean activo, Integer idUsuario,
                                            String fechaDesde, String fechaHasta) throws SQLException;
}
