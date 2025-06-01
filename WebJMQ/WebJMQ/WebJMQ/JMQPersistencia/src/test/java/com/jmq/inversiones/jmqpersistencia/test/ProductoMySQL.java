package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CategoriaDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.CategoriaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoMySQL {

    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;

    @BeforeEach
    public void setUp() {
        productoDAO = new ProductoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Producto prod = crearProductoEjemplo();
        productoDAO.agregar(prod);

        List<Producto> lista = productoDAO.listarTodos();
        boolean encontrado = lista.stream()
                .anyMatch(p -> p.getId() == prod.getId());

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Producto prod = crearProductoEjemplo();
        productoDAO.agregar(prod);

        prod.setNombre("Producto Actualizado");
        prod.setPrecio(99.99);
        productoDAO.actualizar(prod);

        List<Producto> lista = productoDAO.listarTodos();
        Producto actualizado = lista.stream()
                .filter(p -> p.getId() == prod.getId())
                .findFirst().orElse(null);

        assertNotNull(actualizado);
        assertEquals("Producto Actualizado", actualizado.getNombre());
        assertEquals(99.99, actualizado.getPrecio());
    }

    @Test
    public void testEliminar() {
        Producto prod = crearProductoEjemplo();
        productoDAO.agregar(prod);

        productoDAO.eliminar(prod.getId());

        List<Producto> lista = productoDAO.listarTodos();
        boolean eliminado = lista.stream()
                .noneMatch(p -> p.getId() == prod.getId());

        assertTrue(eliminado);
    }

    private Producto crearProductoEjemplo() {
        // ⚠️ Este ID de categoría debe existir o puedes crear una
        Categoria cat = categoriaDAO.listarTodos().stream().findFirst().orElse(null);
        assertNotNull(cat, "No hay categoría disponible");

        Producto p = new Producto();
        p.setNombre("Producto Prueba");
        p.setDescripcion("Descripción prueba");
        p.setStock(20);
        p.setPrecio(50.5);
        p.setImagen("imagen.jpg");
        p.setActivo(true);
        p.setCategoria(cat);
        return p;
    }
}
