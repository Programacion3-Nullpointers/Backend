package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.ComprobantePagoDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.ComprobantePago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testComprobantePagoDAO {

    private ComprobantePagoDAO comprobantePagoDAO;

    @BeforeEach
    public void setUp() {
        comprobantePagoDAO = new ComprobantePagoDAO(); // Implementación necesaria
    }

    @Test
    public void testAgregarYObtener() {
        ComprobantePago comp = new ComprobantePago();
        comp.setIdComprobantePago(1);
        comp.setId_orden(10);
        comp.setMetodo_pago("tarjeta");
        comp.setFecha_pago(Timestamp.valueOf("2025-05-13 10:00:00"));
        comp.setMonto_total(1200.50);

        comprobantePagoDAO.agregar(comp);
        ComprobantePago obtenido = comprobantePagoDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("tarjeta", obtenido.getMetodo_pago());
    }

    @Test
    public void testListarTodos() {
        ComprobantePago c1 = new ComprobantePago();
        c1.setIdComprobantePago(1);
        c1.setId_orden(101);
        c1.setMetodo_pago("efectivo");
        c1.setFecha_pago(Timestamp.valueOf("2025-05-13 11:00:00"));
        c1.setMonto_total(500.0);

        ComprobantePago c2 = new ComprobantePago();
        c2.setIdComprobantePago(2);
        c2.setId_orden(102);
        c2.setMetodo_pago("tarjeta");
        c2.setFecha_pago(Timestamp.valueOf("2025-05-13 12:00:00"));
        c2.setMonto_total(800.0);

        comprobantePagoDAO.agregar(c1);
        comprobantePagoDAO.agregar(c2);

        List<ComprobantePago> lista = comprobantePagoDAO.listarTodos();
        assertTrue(lista.size() >= 2);
    }

    @Test
    public void testActualizar() {
        ComprobantePago comp = new ComprobantePago();
        comp.setIdComprobantePago(3);
        comp.setId_orden(103);
        comp.setMetodo_pago("efectivo");
        comp.setFecha_pago(Timestamp.valueOf("2025-05-13 09:00:00"));
        comp.setMonto_total(100.0);

        comprobantePagoDAO.agregar(comp);

        comp.setMonto_total(150.0);
        comprobantePagoDAO.actualizar(comp);

        ComprobantePago actualizado = comprobantePagoDAO.obtener(3);
        assertEquals(150.0, actualizado.getMonto_total());
    }

    @Test
    public void testEliminar() {
        ComprobantePago comp = new ComprobantePago();
        comp.setIdComprobantePago(4);
        comp.setId_orden(104);
        comp.setMetodo_pago("tarjeta");
        comp.setFecha_pago(Timestamp.valueOf("2025-05-13 13:00:00"));
        comp.setMonto_total(900.0);

        comprobantePagoDAO.agregar(comp);
        comprobantePagoDAO.eliminar(4);

        assertNull(comprobantePagoDAO.obtener(4));
    }
}
