package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.DescuentoDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDescuentoDAO {

    private DescuentoDAO descuentoDAO;

    @BeforeEach
    public void setUp() {
        descuentoDAO = new DescuentoDAOImpl();
    }

    @Test
    public void testAgregarYObtener() {
        Descuento desc = new Descuento();
        desc.setPorcentaje(15);

        descuentoDAO.agregar(desc);

        Descuento obtenido = descuentoDAO.obtener(desc.getId());

        assertNotNull(obtenido);
        assertEquals(15, obtenido.getPorcentaje());
    }

    @Test
    public void testActualizar() {
        Descuento desc = new Descuento();
        desc.setPorcentaje(20);
        descuentoDAO.agregar(desc);

        desc.setPorcentaje(25);
        descuentoDAO.actualizar(desc);

        Descuento actualizado = descuentoDAO.obtener(desc.getId());
        assertEquals(25, actualizado.getPorcentaje());
    }

    @Test
    public void testEliminar() {
        Descuento desc = new Descuento();
        desc.setPorcentaje(30);
        descuentoDAO.agregar(desc);

        descuentoDAO.eliminar(desc.getId());

        Descuento eliminado = descuentoDAO.obtener(desc.getId());
        assertNull(eliminado);
    }

    @Test
    public void testListarTodos() {
        Descuento d1 = new Descuento();
        d1.setPorcentaje(5);

        Descuento d2 = new Descuento();
        d2.setPorcentaje(10);

        descuentoDAO.agregar(d1);
        descuentoDAO.agregar(d2);

        List<Descuento> lista = descuentoDAO.listarTodos();
        assertTrue(lista.size() >= 2);
    }
}
