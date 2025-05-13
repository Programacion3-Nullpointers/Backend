package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.OrdenVenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testOrdenVentaDAO {

    private OrdenVentaDAO ordenVentaDAO;

    @BeforeEach
    public void setUp() {
        ordenVentaDAO = new OrdenVentaDAO(); // Implementación requerida
    }

    @Test
    public void testAgregarYObtener() {
        OrdenVenta orden = new OrdenVenta();
        orden.setIdOrdenVenta(1);
        orden.setEstado_compra("pendiente");
        orden.setFecha_orden(Date.valueOf("2025-05-13"));
        orden.setActivo(1);
        orden.setIdUsuario(1);

        ordenVentaDAO.agregar(orden);
        OrdenVenta obtenido = ordenVentaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("pendiente", obtenido.getEstado_compra());
    }

    @Test
    public void testListarTodos() {
        OrdenVenta o1 = new OrdenVenta();
        o1.setIdOrdenVenta(1);
        o1.setEstado_compra("pendiente");
        o1.setFecha_orden(Date.valueOf("2025-05-13"));
        o1.setIdUsuario(1);

        OrdenVenta o2 = new OrdenVenta();
        o2.setIdOrdenVenta(2);
        o2.setEstado_compra("pagado");
        o2.setFecha_orden(Date.valueOf("2025-05-13"));
        o2.setIdUsuario(1);

        ordenVentaDAO.agregar(o1);
        ordenVentaDAO.agregar(o2);

        List<OrdenVenta> ordenes = ordenVentaDAO.listarTodos();
        assertTrue(ordenes.size() >= 2);
    }

    @Test
    public void testActualizar() {
        OrdenVenta orden = new OrdenVenta();
        orden.setIdOrdenVenta(1);
        orden.setEstado_compra("pendiente");
        orden.setFecha_orden(Date.valueOf("2025-05-13"));
        orden.setIdUsuario(1);

        ordenVentaDAO.agregar(orden);

        orden.setEstado_compra("enviado");
        ordenVentaDAO.actualizar(orden);

        OrdenVenta actualizado = ordenVentaDAO.obtener(1);
        assertEquals("enviado", actualizado.getEstado_compra());
    }

    @Test
    public void testEliminar() {
        OrdenVenta orden = new OrdenVenta();
        orden.setIdOrdenVenta(1);
        orden.setEstado_compra("eliminar");
        orden.setFecha_orden(Date.valueOf("2025-05-13"));
        orden.setIdUsuario(1);

        ordenVentaDAO.agregar(orden);
        ordenVentaDAO.eliminar(1);

        assertNull(ordenVentaDAO.obtener(1));
    }
}
