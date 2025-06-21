package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.ventas.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoService {
    void registrarProducto(Producto p) throws Exception;
    void actualizarProducto(Producto p) throws Exception;
    void eliminarProducto(int id) throws Exception;
    Producto buscarProducto(int id) throws Exception;
    List<Producto> listarProductos() throws Exception;
    void descontarStock(int id, int stock);
    List<Producto> filtrarProductos(String nombreCategoria, Boolean activo, 
            Double precioMin, Double precioMax, Integer stockMin, 
            Integer stockMax,Boolean conDescuento) throws SQLException;
}
