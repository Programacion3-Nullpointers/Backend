package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.List;


public interface OrdenVentaDAO extends BaseDAO<OrdenVenta>{
    List<OrdenVenta> listarPorUsuario(int idUsuario);
}
