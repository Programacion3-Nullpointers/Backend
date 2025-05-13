package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Detalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDetalleDAO {

    private DetalleDAO detalleDAO;

    @BeforeEach
    public void setUp() {
        detalleDAO = new DetalleDAO(); // Asegúrate de tener métodos que reciban dos claves
    }

    @Test
    public void testAgregarYObtener() {
        Detalle detalle = new Detalle();
        detalle.setId_orden(1);
        detalle.setId_producto(101);
        detalle.setCantidad(2);
        detalle.setPrecio_unitario(1750.25);

        detalleDAO.agregar(detalle);
        Detalle obtenido = detalleDAO.obtener(1, 101); // Usa ambas claves

        assertNotNull(obtenido);
        assertEquals(2, obtenido.getCantidad());
    }

    @Test
    public void testListarTodos() {
        Detalle d1 = new Detalle();
        d1.setId_orden(1);
        d1.setId_producto(201);
        d1.setCantidad(1);
        d1.setPrecio_unitario(100.0);

        Detalle d2 = new Detalle();
        d2.setId_orden(2);
        d2.setId_producto(202);
        d2.setCantidad(3);
        d2.setPrecio_unitario(300.0);

        detalleDAO.agregar(d1);
        detalleDAO.agregar(d2);

        List<Detalle> detalles = detalleDAO.listarTodos();
        assertTrue(detalles.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Detalle detalle = new Detalle();
        detalle.setId_orden(3);
        detalle.setId_producto(301);
        detalle.setCantidad(2);
        detalle.setPrecio_unitario(200.0);

        detalleDAO.agregar(detalle);

        detalle.setCantidad(5);
        detalle.setPrecio_unitario(500.0);
        detalleDAO.actualizar(detalle);

        Detalle actualizado = detalleDAO.obtener(3, 301);
        assertEquals(5, actualizado.getCantidad());
        assertEquals(500.0, actualizado.getPrecio_unitario());
    }

    @Test
    public void testEliminar() {
        Detalle detalle = new Detalle();
        detalle.setId_orden(4);
        detalle.setId_producto(401);
        detalle.setCantidad(2);
        detalle.setPrecio_unitario(100.0);

        detalleDAO.agregar(detalle);
        detalleDAO.eliminar(4, 401);

        assertNull(detalleDAO.obtener(4, 401));
    }
}
