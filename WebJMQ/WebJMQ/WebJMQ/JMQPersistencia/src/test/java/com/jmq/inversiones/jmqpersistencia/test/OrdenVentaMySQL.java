package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.OrdenVentaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenVentaMySQL {

    private OrdenVentaDAO ordenVentaDAO;

    @BeforeEach
    public void setUp() {
        ordenVentaDAO = new OrdenVentaDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        OrdenVenta orden = crearOrdenEjemplo(EstadoCompra.pendiente);
        ordenVentaDAO.agregar(orden);

        List<OrdenVenta> lista = ordenVentaDAO.listarTodos();
        boolean encontrado = lista.stream()
                .anyMatch(o -> o.getId() == orden.getId());

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        OrdenVenta orden = crearOrdenEjemplo(EstadoCompra.pendiente);
        ordenVentaDAO.agregar(orden);

        orden.setEstado_compra(EstadoCompra.pagado);
        orden.setActivo(false);
        ordenVentaDAO.actualizar(orden);

        List<OrdenVenta> lista = ordenVentaDAO.listarTodos();
        OrdenVenta actualizado = lista.stream()
                .filter(o -> o.getId() == orden.getId())
                .findFirst().orElse(null);

        assertNotNull(actualizado);
        assertEquals(EstadoCompra.pagado, actualizado.getEstado_compra());
        assertFalse(actualizado.isActivo());
    }

    @Test
    public void testEliminar() {
        OrdenVenta orden = crearOrdenEjemplo(EstadoCompra.enviado);
        ordenVentaDAO.agregar(orden);

        ordenVentaDAO.eliminar(orden.getId());

        List<OrdenVenta> lista = ordenVentaDAO.listarTodos();
        boolean eliminado = lista.stream()
                .noneMatch(o -> o.getId() == orden.getId());

        assertTrue(eliminado);
    }

    private OrdenVenta crearOrdenEjemplo(EstadoCompra estado) {
        Usuario u = new Usuario();
        u.setId(1); // ⚠️ Usuario con ID 1 debe existir

        OrdenVenta orden = new OrdenVenta();
        orden.setUsuario(u);
        orden.setEstado_compra(estado);
        orden.setFecha_orden(new Date());
        orden.setActivo(true);

        return orden;
    }
}
