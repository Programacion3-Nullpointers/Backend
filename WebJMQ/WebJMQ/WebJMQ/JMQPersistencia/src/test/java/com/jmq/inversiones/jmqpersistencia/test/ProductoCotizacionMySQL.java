package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoCotizacionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoCotizacionMySQL {

    private ProductoCotizacionDAOImpl dao;

    @BeforeEach
    public void setUp() {
        dao = new ProductoCotizacionDAOImpl();
    }

//    @Test
//    public void testAgregarYObtenerPorCotizacion() {
//        int idCotizacion = 1; // ⚠️ asegúrate que existe en tu BD
//
//        ProductoCotizacion prod = new ProductoCotizacion();
//        prod.setId(9999); // usa un ID alto para evitar colisiones
//        prod.setDescripcion("Producto de prueba");
//        prod.setCantidad(5);
//        prod.setPrecioCotizado(120.50);
//
//        dao.agregar(prod, idCotizacion);
//
//        List<ProductoCotizacion> lista = dao.obtenerPorCotizacion(idCotizacion);
//        boolean encontrado = lista.stream()
//                .anyMatch(p -> p.getId() == prod.getId());
//
//        assertTrue(encontrado, "Producto cotizado no fue encontrado");
//    }
//
//    @Test
//    public void testEliminarPorCotizacion() {
//        int idCotizacion = 1;
//
//        // Agrega 2 productos
//        ProductoCotizacion p1 = new ProductoCotizacion(8001, "Eliminar 1", 2, 50.0, null);
//        ProductoCotizacion p2 = new ProductoCotizacion(8002, "Eliminar 2", 3, 75.0, null);
//
//        dao.agregar(p1, idCotizacion);
//        dao.agregar(p2, idCotizacion);
//
//        dao.eliminar(idCotizacion);
//
//        List<ProductoCotizacion> lista = dao.obtenerPorCotizacion(idCotizacion);
//        assertTrue(lista.isEmpty(), "No se eliminaron productos cotizados");
//    }
}
