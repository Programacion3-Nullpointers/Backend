package com.jmq.inversiones.jmqpersistencia.dao.ventas;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.Detalle;
import java.util.List;


public interface DetalleDAO extends BaseDAO<Detalle>{
    void eliminar(int idOrden, int idProducto);
    Detalle obtener(int idOrden, int idProducto);
    List<Detalle> listarPorOrden(int idOrden);
}
