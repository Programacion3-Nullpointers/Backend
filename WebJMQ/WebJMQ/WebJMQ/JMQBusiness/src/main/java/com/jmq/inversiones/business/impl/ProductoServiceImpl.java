package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.ProductoService;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoServiceImpl implements ProductoService{

    private final ProductoDAO productoDAO;

    public ProductoServiceImpl(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public void registrarProducto(Producto producto) throws Exception {
        try {
            // Validaciones básicas
            if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
                throw new Exception("El nombre del producto es requerido");
            }
            if (producto.getPrecio() <= 0) {
                throw new Exception("El precio debe ser mayor que cero");
            }
            if (producto.getStock() < 0) {
                throw new Exception("El stock no puede ser negativo");
            }
            if (producto.getCategoria() == null) {
                throw new Exception("La categoría es requerida");
            }
            
            if (producto.getImagen() == null) {
               try {
                    byte[] imagenBytes = Files.readAllBytes(Paths.get("C:/ruta/a/tu/default.png"));
                    producto.setImagen(imagenBytes);
                } catch (IOException e) {
                    throw new RuntimeException("Error leyendo la imagen por defecto", e);
                }
            }
            if (!producto.isActivo()) {
                producto.setActivo(true); // Por defecto activo al registrar
            }
            
            // Llamar al DAO
            productoDAO.agregar(producto);
        } catch (Exception e) {
            throw new Exception("Error al registrar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarProducto(Producto producto) throws Exception {
        try {
            // Validaciones
            if (producto.getId() <= 0) {
                throw new Exception("ID de producto inválido");
            }
            if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
                throw new Exception("El nombre del producto es requerido");
            }
            if (producto.getPrecio() <= 0) {
                throw new Exception("El precio debe ser mayor que cero");
            }
            
            // Llamar al DAO
            productoDAO.actualizar(producto);
        } catch (Exception e) {
            throw new Exception("Error al actualizar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarProducto(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de producto inválido");
            }
            
            // Llamar al DAO
            productoDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Producto buscarProducto(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de producto inválido");
            }
            
            // Llamar al DAO
            return productoDAO.obtener(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Producto> listarProductos() throws Exception {
        try {
            return productoDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar productos: " + e.getMessage(), e);
        }
    }

    @Override
    public void descontarStock(int id, int stock) {
        try {
            if(stock>=1){
                productoDAO.descontarStock(id, stock);
            }
            else{
                throw new Exception("Error al descontarStock");
            }
        } catch (Exception e) {
            try {
                throw new Exception();
            } catch (Exception ex) {
                Logger.getLogger(ProductoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Producto> filtrarProductos(String nombreCategoria, Boolean activo, 
            Double precioMin, Double precioMax, Integer stockMin, 
            Integer stockMax,Boolean conDescuento) throws SQLException {
        try {
            return productoDAO.filtrarProductos(nombreCategoria, activo, precioMin, precioMax, stockMin, stockMax, conDescuento);
        } catch (SQLException e){
            throw e;
        }
    }
}
