package com.jmq.inversiones.jmqpersistencia.test;



import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoCotizacionDAOImpl;
import java.util.ArrayList;
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
        pc.setFid_cotizacion(1);
        dao.agregar(pc);

        ProductoCotizacion prueba = dao.obtener(pc.getId());
        
        assertNotNull(prueba, "El producto de la cotizacion debe existir después de agregarlo");
        
        //assertTrue(encontrado, "El producto cotizado no se encontró luego de agregar");
    }

    @Test
    public void testListarPorCotizacion(){
        List<ProductoCotizacion> prods = new ArrayList<>();
        int inicio = prods.size();
        prods = dao.listarPorCotizacion(1);
        
        int fin =prods.size();
        assertNotEquals(inicio,fin);
    }
    
   @Test
    public void testActualizarPrecioCotizado() {
        // Primero agregamos un producto para asegurarnos de que existe
        ProductoCotizacion pc = new ProductoCotizacion();
        pc.setDescripcion("Producto actualizable");
        pc.setCantidad(2);
        pc.setPrecioCotizado(100.0); // precio inicial
        pc.setFid_cotizacion(1);
        dao.agregar(pc); // se guarda y se le asigna ID

        int idProducto = pc.getId(); // obtener el ID asignado
        double nuevoPrecio = 345.75;

        // Ejecutamos el método a probar
        dao.actualizarPrecioCotizacion(pc, nuevoPrecio);

        // Verificamos
        ProductoCotizacion actualizado = dao.obtener(idProducto);

        assertNotNull(actualizado, "El producto actualizado no fue encontrado");
        assertEquals(nuevoPrecio, actualizado.getPrecioCotizado(), 0.01);
    }



    @Test
    public void testEliminarPorCotizacion() {
        ProductoCotizacion p1 = new ProductoCotizacion( "Eliminar P1", 2, 50.0, idCotizacionDummy);
        ProductoCotizacion p2 = new ProductoCotizacion( "Eliminar P2", 3, 75.0, idCotizacionDummy);
        int cantidad_Pre_inserts = dao.listarTodos().size();
        dao.agregar(p1);
        dao.agregar(p2);

        dao.eliminar(p1.getId());
        dao.eliminar(p2.getId());
        List<ProductoCotizacion> lista = dao.listarTodos();
        assertTrue(lista.size()==cantidad_Pre_inserts, "Los productos cotizados no fueron eliminados correctamente");
    }
}
