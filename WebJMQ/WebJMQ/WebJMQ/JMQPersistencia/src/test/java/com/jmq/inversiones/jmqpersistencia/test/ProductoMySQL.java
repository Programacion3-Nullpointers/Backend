package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CategoriaDAOImpl;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void testAgregarYObtenerYListar()throws URISyntaxException, IOException  {
        Producto prod = crearProductoEjemplo();
        productoDAO.agregar(prod);

        assertTrue(prod.getId() > 0, "El ID generado debe ser mayor a 0");

        Producto obtenido = productoDAO.obtener(prod.getId());
        assertNotNull(obtenido, "El producto debe existir después de agregarlo");

        List<Producto> lista = productoDAO.listarTodos();
        assertTrue(lista.stream().anyMatch(p -> p.getId() == prod.getId()));
    }

    @Test
    public void testActualizar()throws URISyntaxException, IOException  {
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
    public void testEliminar() throws URISyntaxException, IOException {
        Producto prod = crearProductoEjemplo();//crea un nuevo producto y lo elimina a la vez
        productoDAO.agregar(prod);

        productoDAO.eliminar(prod.getId());

        Producto eliminado = productoDAO.obtener(prod.getId());
        assertNull(eliminado, "Después de eliminar, no debería encontrarse el producto activo");

        List<Producto> lista = productoDAO.listarTodos();
        assertFalse(lista.stream().anyMatch(p -> p.getId() == prod.getId()));
    }
     @Test
    public void testFiltrarPorCategoria() throws Exception {
        String categoria = "Electrohogar Test";
        List<Producto> resultados = productoDAO.filtrarProductos(categoria, null, null, null,null,null,null);

        assertNotNull(resultados);
        for (Producto p : resultados) {
            assertEquals(categoria, p.getCategoria().getNombre());
        }
    }

    @Test
    public void testFiltrarPorActivo() throws Exception {
        List<Producto> resultados = productoDAO.filtrarProductos(null, true, null, null,null,null,null);

        assertNotNull(resultados);
        for (Producto p : resultados){
            
            assertTrue(p.isActivo());
        }
    }

    @Test
    public void testFiltrarPorRangoPrecio() throws Exception {
        double min = 30.0;
        double max = 100.0;
        List<Producto> resultados = productoDAO.filtrarProductos(null, null, min, max,null,null,null);

        assertNotNull(resultados);
        for (Producto p : resultados) {
            System.out.println(p);
            assertTrue(p.getPrecio() >= min && p.getPrecio() <= max);
        }
    }

    @Test
    public void testFiltrarTodoCombinado() throws Exception {
        List<Producto> resultados = productoDAO.filtrarProductos("Electrohogar Test", true, 30.0, 60.0,null,null,null);

        assertNotNull(resultados);
        for (Producto p : resultados) {
            System.out.println(p);
            assertEquals("Electrohogar Test", p.getCategoria().getNombre());
            assertTrue(p.isActivo());
            assertTrue(p.getPrecio() >= 30.0 && p.getPrecio() <= 100.0);
        }
    }

    @Test
    public void testSinFiltros() throws Exception {
        List<Producto> resultados = productoDAO.filtrarProductos(null, null, null, null,null,null,null);
         for (Producto p : resultados) {
            System.out.println(p);
        }
        assertNotNull(resultados);
        assertFalse(resultados.isEmpty()); // Espera al menos un producto
    }
    private Producto crearProductoEjemplo() throws IOException, URISyntaxException {
        Categoria cat = categoriaDAO.listarTodos().stream().findFirst().orElse(null);
        assertNotNull(cat, "Debe existir al menos una categoría válida en la BD");

        Producto p = new Producto();
        p.setNombre("Producto Prueba");
        p.setDescripcion("Descripción prueba");
        p.setStock(20);
        p.setPrecio(50.5);
        Path imagePath = Paths.get("pucp.png");
        byte[] imageBytes = Files.readAllBytes(imagePath);
        p.setImagen(imageBytes);
        p.setActivo(true);
        p.setCategoria(cat);
        return p;
    }
}
