package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.logistica.Entrega;
import com.jmq.inversiones.dominio.logistica.TipoEntrega;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.EntregaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.EntregaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntregaMySQL {

    private EntregaDAO entregaDAO;

    @BeforeEach
    public void setUp() {
        entregaDAO = new EntregaDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Entrega entrega = crearEntregaEjemplo(1);
        entregaDAO.agregar(entrega);

        List<Entrega> entregas = entregaDAO.listarTodos();
        boolean encontrado = entregas.stream()
                .anyMatch(e -> e.getOrden().getId() == entrega.getOrden().getId() &&
                               e.getDireccion().equals(entrega.getDireccion()));

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Entrega entrega = crearEntregaEjemplo(2);
        entregaDAO.agregar(entrega);

        entrega.setDireccion("Calle Nueva 123");
        entrega.setDniRecibo("76543210");
        entregaDAO.actualizar(entrega);

        List<Entrega> entregas = entregaDAO.listarTodos();
        Entrega actualizado = entregas.stream()
                .filter(e -> e.getId() == entrega.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(actualizado);
        assertEquals("Calle Nueva 123", actualizado.getDireccion());
        assertEquals(76543210, actualizado.getDniRecibo());
    }

    @Test
    public void testEliminar() {
        Entrega entrega = crearEntregaEjemplo(3);
        entregaDAO.agregar(entrega);

        entregaDAO.eliminar(entrega.getId());

        List<Entrega> entregas = entregaDAO.listarTodos();
        boolean eliminado = entregas.stream()
                .noneMatch(e -> e.getId() == entrega.getId());

        assertTrue(eliminado);
    }

    private Entrega crearEntregaEjemplo(int ordenId) {
        OrdenVenta orden = new OrdenVenta();
        orden.setId(ordenId); // ⚠️ Asegúrate de que este ID exista en la BD

        Entrega entrega = new Entrega();
        entrega.setOrden(orden);
        entrega.setFecha_entrega(new Date());
        entrega.setDireccion("Av. Test #" + ordenId);
        entrega.setDniRecibo("12345678");
        entrega.setTipoEntrega(TipoEntrega.DELIVERY); // o RECOJO
        return entrega;
    }
}
