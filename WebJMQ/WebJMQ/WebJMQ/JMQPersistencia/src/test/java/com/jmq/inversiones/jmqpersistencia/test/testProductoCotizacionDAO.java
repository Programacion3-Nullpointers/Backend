package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.ProductoCotizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testProductoCotizacionDAO {

    private ProductoCotizacionDAO productoCotizacionDAO;

    @BeforeEach
    public void setUp() {
        productoCotizacionDAO = new ProductoCotizacionDAO(); // Asegúrate que esté correctamente implementado
    }

    @Test
    public void testAgregarYObtener() {
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setIdproductoCotizado(1);
        pc.setDescripcion("Laptop cotizada");
        pc.setCantidad(2);
        pc.setPrecioCotizado(4500.0);
        pc.setIdCotizacion(1);

        productoCotizacionDAO.agregar(pc);
        ProductoCotizacion obtenido = productoCotizacionDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Laptop cotizada", obtenido.getDescripcion());
    }

    @Test
    public void testListarTodos() {
        ProductoCotizacion p1 = new ProductoCotizacion();
        p1.setIdproductoCotizado(2);
        p1.setDescripcion("Producto A");
        p1.setCantidad(1);
        p1.setPrecioCotizado(100.0);
        p1.setIdCotizacion(2);

        ProductoCotizacion p2 = new ProductoCotizacion();
        p2.setIdproductoCotizado(3);
        p2.setDescripcion("Producto B");
        p2.setCantidad(3);
        p2.setPrecioCotizado(300.0);
        p2.setIdCotizacion(2);

        productoCotizacionDAO.agregar(p1);
        productoCotizacionDAO.agregar(p2);

        List<ProductoCotizacion> lista = productoCotizacionDAO.listarTodos();
        assertTrue(lista.size() >= 2);
    }

    @Test
    public void testActualizar() {
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setIdproductoCotizado(4);
        pc.setDescripcion("Producto X");
        pc.setCantidad(1);
        pc.setPrecioCotizado(500.0);
        pc.setIdCotizacion(3);

        productoCotizacionDAO.agregar(pc);

        pc.setCantidad(5);
        pc.setPrecioCotizado(2500.0);
        productoCotizacionDAO.actualizar(pc);

        ProductoCotizacion actualizado = productoCotizacionDAO.obtener(4);
        assertEquals(5, actualizado.getCantidad());
        assertEquals(2500.0, actualizado.getPrecioCotizado());
    }

    @Test
    public void testEliminar() {
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setIdproductoCotizado(5);
        pc.setDescripcion("Producto a eliminar");
        pc.setCantidad(1);
        pc.setPrecioCotizado(100.0);
        pc.setIdCotizacion(4);

        productoCotizacionDAO.agregar(pc);
        productoCotizacionDAO.eliminar(5);

        assertNull(productoCotizacionDAO.obtener(5));
    }
}
