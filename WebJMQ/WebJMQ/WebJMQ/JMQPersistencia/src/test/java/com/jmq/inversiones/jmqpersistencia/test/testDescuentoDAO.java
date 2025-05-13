package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Descuento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDescuentoDAO {

    private DescuentoDAO descuentoDAO;

    @BeforeEach
    public void setUp() {
        descuentoDAO = new DescuentoDAO(); // Implementación necesaria para pruebas
    }

    @Test
    public void testAgregarYObtener() {
        Descuento desc = new Descuento();
        desc.setIdDescuento(1);
        desc.setNumDescuento(10);
        desc.setActivo(1);

        descuentoDAO.agregar(desc);
        Descuento obtenido = descuentoDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals(10, obtenido.getNumDescuento());
    }

    @Test
    public void testListarTodos() {
        Descuento d1 = new Descuento();
        d1.setIdDescuento(1);
        d1.setNumDescuento(10);

        Descuento d2 = new Descuento();
        d2.setIdDescuento(2);
        d2.setNumDescuento(20);

        descuentoDAO.agregar(d1);
        descuentoDAO.agregar(d2);

        List<Descuento> descuentos = descuentoDAO.listarTodos();
        assertTrue(descuentos.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Descuento desc = new Descuento();
        desc.setIdDescuento(1);
        desc.setNumDescuento(10);

        descuentoDAO.agregar(desc);

        desc.setNumDescuento(15);
        descuentoDAO.actualizar(desc);

        Descuento actualizado = descuentoDAO.obtener(1);
        assertEquals(15, actualizado.getNumDescuento());
    }

    @Test
    public void testEliminar() {
        Descuento desc = new Descuento();
        desc.setIdDescuento(1);
        desc.setNumDescuento(5);

        descuentoDAO.agregar(desc);
        descuentoDAO.eliminar(1);

        assertNull(descuentoDAO.obtener(1));
    }
}
