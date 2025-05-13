package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Factura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testFacturaDAO {

    private FacturaDAO facturaDAO;

    @BeforeEach
    public void setUp() {
        facturaDAO = new FacturaDAO(); // Implementación necesaria
    }

    @Test
    public void testAgregarYObtener() {
        Factura factura = new Factura();
        factura.setIdFactura(1); // También es idComprobantePago
        factura.setRUC("12345678901");
        factura.setRazon_social("Empresa SAC");
        factura.setDireccion_fiscal("Av. Empresa 789");
        factura.setFecha_emision(Timestamp.valueOf("2025-05-13 11:00:00"));

        facturaDAO.agregar(factura);
        Factura obtenido = facturaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Empresa SAC", obtenido.getRazon_social());
    }

    @Test
    public void testListarTodos() {
        Factura f1 = new Factura();
        f1.setIdFactura(1);
        f1.setRUC("11111111111");
        f1.setRazon_social("Empresa A");
        f1.setDireccion_fiscal("Calle Uno");
        f1.setFecha_emision(Timestamp.valueOf("2025-05-14 09:00:00"));

        Factura f2 = new Factura();
        f2.setIdFactura(2);
        f2.setRUC("22222222222");
        f2.setRazon_social("Empresa B");
        f2.setDireccion_fiscal("Calle Dos");
        f2.setFecha_emision(Timestamp.valueOf("2025-05-14 10:00:00"));

        facturaDAO.agregar(f1);
        facturaDAO.agregar(f2);

        List<Factura> facturas = facturaDAO.listarTodos();
        assertTrue(facturas.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Factura factura = new Factura();
        factura.setIdFactura(3);
        factura.setRUC("33333333333");
        factura.setRazon_social("Temporal");
        factura.setDireccion_fiscal("Original");
        factura.setFecha_emision(Timestamp.valueOf("2025-05-14 11:00:00"));

        facturaDAO.agregar(factura);

        factura.setDireccion_fiscal("Nueva Dirección");
        facturaDAO.actualizar(factura);

        Factura actualizado = facturaDAO.obtener(3);
        assertEquals("Nueva Dirección", actualizado.getDireccion_fiscal());
    }

    @Test
    public void testEliminar() {
        Factura factura = new Factura();
        factura.setIdFactura(4);
        factura.setRUC("44444444444");
        factura.setRazon_social("Eliminar");
        factura.setDireccion_fiscal("Eliminar Dirección");
        factura.setFecha_emision(Timestamp.valueOf("2025-05-14 12:00:00"));

        facturaDAO.agregar(factura);
        facturaDAO.eliminar(4);

        assertNull(facturaDAO.obtener(4));
    }
}
