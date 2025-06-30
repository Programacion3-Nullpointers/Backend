package com.jmq.inversiones.jmqpersistencia.dao.ventas;
import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.ventas.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO extends BaseDAO<Producto>{
    void descontarStock(int id, int stock);
    List<Producto> filtrarProductos(String nombreCategoria, Boolean activo, 
            Double precioMin, Double precioMax, 
            Integer stockMin, Integer stockMax,Boolean conDescuento) throws SQLException;
}
