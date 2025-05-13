package com.jmq.inversiones.jmqpersistencia.dao;
import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.Producto;

public interface ProductoDAO extends BaseDAO<Producto>{
    void descontarStock(int id, int stock);
}
