package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testProductoDAO {

    private ProductoDAO productoDAO;

    @BeforeEach
    public void setUp() {
        productoDAO = new ProductoDAO(); // Asegúrate que esta clase tenga implementación válida
    }

    @Test
    public void testAgregarYObtener() {
        Producto prod = new Producto();
        prod.setIdProducto(1);
        prod.setNombre("Laptop");
        prod.setDescripcion("Portátil 15 pulgadas");
        prod.setStock(10);
        prod.setPrecio(3500.50);
        prod.setImagen("imagen.jpg");
        prod.setActivo(1);
        prod.setIdCategoria(1);

        productoDAO.agregar(prod);
        Producto obtenido = productoDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Laptop", obtenido.getNombre());
    }

    @Test
    public void testListarTodos() {
        Producto p1 = new Producto();
        p1.setIdProducto(2);
        p1.setNombre("Producto A");

        Producto p2 = new Producto();
        p2.setIdProducto(3);
        p2.setNombre("Producto B");

        productoDAO.agregar(p1);
        productoDAO.agregar(p2);

        List<Producto> productos = productoDAO.listarTodos();
        assertTrue(productos.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Producto prod = new Producto();
        prod.setIdProducto(4);
        prod.setNombre("Tablet");
        prod.setPrecio(2000.00);

        productoDAO.agregar(prod);

        prod.setPrecio(1800.00);
        productoDAO.actualizar(prod);

        Producto actualizado = productoDAO.obtener(4);
        assertEquals(1800.00, actualizado.getPrecio());
    }

    @Test
    public void testEliminar() {
        Producto prod = new Producto();
        prod.setIdProducto(5);
        prod.setNombre("Eliminar");

        productoDAO.agregar(prod);
        productoDAO.eliminar(5);

        assertNull(productoDAO.obtener(5));
    }
}
