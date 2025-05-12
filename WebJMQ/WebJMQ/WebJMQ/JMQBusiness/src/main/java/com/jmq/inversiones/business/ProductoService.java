package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.ventas.Producto;
import java.util.List;

public interface ProductoService {
    void registrarProducto(Producto p) throws Exception;
    void actualizarProducto(Producto p) throws Exception;
    void eliminarProducto(int id) throws Exception;
    Producto buscarProducto(int id) throws Exception;
    List<Producto> listarProductos() throws Exception;
    
}
