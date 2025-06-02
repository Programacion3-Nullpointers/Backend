package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoCotizacionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoCotizacionMySQL {

    private ProductoCotizacionDAOImpl dao;
    private final int idCotizacionDummy = 1; // ⚠️ debe existir o debes crearlo manualmente

    @BeforeEach
    public void setUp() {
        dao = new ProductoCotizacionDAOImpl();
    }

    @Test
    public void testAgregarYObtenerPorCotizacion() {
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setDescripcion("Producto solicitado por cliente");
        pc.setCantidad(3);
        pc.setPrecioCotizado(0.0); // inicialmente sin precio

        dao.agregar(pc, idCotizacionDummy);

        List<ProductoCotizacion> lista = dao.obtenerPorCotizacion(idCotizacionDummy);
        boolean encontrado = lista.stream()
                .anyMatch(p -> p.getId() == pc.getId());

        assertTrue(encontrado, "El producto cotizado no se encontró luego de agregar");
    }

   @Test
    public void testActualizarPrecioCotizado() {
        // Primero agregamos un producto para asegurarnos de que existe
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setDescripcion("Producto actualizable");
        pc.setCantidad(2);
        pc.setPrecioCotizado(100.0); // precio inicial
        dao.agregar(pc, idCotizacionDummy); // se guarda y se le asigna ID

        int idProducto = pc.getId(); // obtener el ID asignado
        double nuevoPrecio = 345.75;

        // Ejecutamos el método a probar
        dao.actualizarPrecioCotizacion(idProducto, idCotizacionDummy, nuevoPrecio);

        // Verificamos
        ProductoCotizacion actualizado = dao.obtenerPorIdYCotizacion(idProducto, idCotizacionDummy);

        assertNotNull(actualizado, "El producto actualizado no fue encontrado");
        assertEquals(nuevoPrecio, actualizado.getPrecioCotizado(), 0.01);
    }



    @Test
    public void testEliminarPorCotizacion() {
        ProductoCotizacion p1 = new ProductoCotizacion(8001, "Eliminar P1", 2, 50.0, idCotizacionDummy);
        ProductoCotizacion p2 = new ProductoCotizacion(8002, "Eliminar P2", 3, 75.0, idCotizacionDummy);

        dao.agregar(p1, idCotizacionDummy);
        dao.agregar(p2, idCotizacionDummy);

        dao.eliminar(idCotizacionDummy);

        List<ProductoCotizacion> lista = dao.obtenerPorCotizacion(idCotizacionDummy);
        assertTrue(lista.isEmpty(), "Los productos cotizados no fueron eliminados correctamente");
    }
}
