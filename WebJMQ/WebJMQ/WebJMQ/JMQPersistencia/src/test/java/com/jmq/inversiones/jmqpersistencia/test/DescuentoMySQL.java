package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.DescuentoDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DescuentoMySQL {

    private DescuentoDAO descuentoDAO;

    @BeforeEach
    public void setUp() {
        descuentoDAO = new DescuentoDAOImpl();
    }

    @Test
    public void testAgregarYObtener() {
        Descuento desc = new Descuento();
        desc.setNumDescuento(15);
        desc.setActivo(true);

        descuentoDAO.agregar(desc);

        List<Descuento> descuentos = descuentoDAO.listarTodos();
        assertNotNull(descuentos);
        assertTrue(descuentos.stream().anyMatch(u -> u.getId() == desc.getId()));
    }

    @Test
    public void testActualizar() {
        Descuento desc = new Descuento();
        desc.setNumDescuento(20);
        descuentoDAO.agregar(desc);

        desc.setNumDescuento(25);
        descuentoDAO.actualizar(desc);

        Descuento actualizado = descuentoDAO.obtener(desc.getId());
        assertEquals(25, actualizado.getNumDescuento());
    }

    @Test
    public void testEliminar() {
        Descuento desc = new Descuento();
        desc.setNumDescuento(30);
        descuentoDAO.agregar(desc);

        descuentoDAO.eliminar(desc.getId());

        Descuento eliminado = descuentoDAO.obtener(desc.getId());
        assertEquals(eliminado.isActivo(),false);
    }

    @Test
    public void testListarTodos() {
//        Descuento d1 = new Descuento();
//        d1.setNumDescuento(5);
//
//        Descuento d2 = new Descuento();
//        d2.setNumDescuento(10);
//
//        descuentoDAO.agregar(d1);
//        descuentoDAO.agregar(d2);

        List<Descuento> lista = descuentoDAO.listarTodos();
        System.out.println(lista.size());
    }
    @Test
    public void Activar() throws Exception{
        descuentoDAO.activarDescuento(2);
        System.out.println(descuentoDAO.obtener(2));
    }
    @Test
    public void Dividir() throws Exception{
         List<Descuento> lista = descuentoDAO.filtrarDescuentos(Boolean.FALSE, 0, 100);
        System.out.println(lista.size());
    }
}
