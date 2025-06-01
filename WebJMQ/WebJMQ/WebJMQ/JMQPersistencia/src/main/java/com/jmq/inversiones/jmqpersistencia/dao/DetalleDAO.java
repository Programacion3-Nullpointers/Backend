package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.Detalle;


public interface DetalleDAO extends BaseDAO<Detalle>{
    void eliminar(int idOrden, int idProducto);
    Detalle obtener(int idOrden, int idProducto);
}
