package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CategoriaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoMySQL {

    private ProductoDAO productoDAO;
    private CategoriaDAOImpl categoriaDAO;

    @BeforeEach
    public void setUp() {
        productoDAO = new ProductoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
    }

    @Test
    public void testAgregarYObtenerYListar() {
        Producto prod = crearProductoEjemplo();
        productoDAO.agregar(prod);

        assertTrue(prod.getId() > 0, "El ID generado debe ser mayor a 0");

        Producto obtenido = productoDAO.obtener(prod.getId());
        assertNotNull(obtenido, "El producto debe existir después de agregarlo");

        List<Producto> lista = productoDAO.listarTodos();
        assertTrue(lista.stream().anyMatch(p -> p.getId() == prod.getId()));
    }

    @Test
    public void testActualizar() {
        Producto prod = crearProductoEjemplo(); 
        productoDAO.agregar(prod);

        prod.setNombre("Producto Actualizado");
        prod.setPrecio(99.99);
        productoDAO.actualizar(prod);

        Producto actualizado = productoDAO.obtener(prod.getId());
        assertNotNull(actualizado);
        assertEquals("Producto Actualizado", actualizado.getNombre());
        assertEquals(99.99, actualizado.getPrecio());
    }

    @Test
    public void testEliminar() {
        Producto prod = crearProductoEjemplo();//crea un nuevo producto y lo elimina a la vez
        productoDAO.agregar(prod);

        productoDAO.eliminar(prod.getId());

        Producto eliminado = productoDAO.obtener(prod.getId());
        assertNull(eliminado, "Después de eliminar, no debería encontrarse el producto activo");

        List<Producto> lista = productoDAO.listarTodos();
        assertFalse(lista.stream().anyMatch(p -> p.getId() == prod.getId()));
    }

    private Producto crearProductoEjemplo() {
        Categoria cat = categoriaDAO.listarTodos().stream().findFirst().orElse(null);
        assertNotNull(cat, "Debe existir al menos una categoría válida en la BD");

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
