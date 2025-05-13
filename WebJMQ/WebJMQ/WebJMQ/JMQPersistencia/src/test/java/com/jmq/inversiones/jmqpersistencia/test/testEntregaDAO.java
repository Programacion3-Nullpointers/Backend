package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.EntregaDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Entrega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testEntregaDAO {

    private EntregaDAO entregaDAO;

    @BeforeEach
    public void setUp() {
        entregaDAO = new EntregaDAO(); // Implementación concreta requerida
    }

    @Test
    public void testAgregarYObtener() {
        Entrega entrega = new Entrega();
        entrega.setIdEntrega(1);
        entrega.setId_orden(1001);
        entrega.setFecha_entrega(Timestamp.valueOf("2025-05-15 12:00:00"));
        entrega.setDireccion("Av. Los Rosales 123");
        entrega.setDni(12345678);
        entrega.setTipoEntrega("DELIVERY");

        entregaDAO.agregar(entrega);
        Entrega obtenido = entregaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("DELIVERY", obtenido.getTipoEntrega());
    }

    @Test
    public void testListarTodos() {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        e1.setId_orden(2001);
        e1.setFecha_entrega(Timestamp.valueOf("2025-05-16 10:00:00"));
        e1.setDireccion("Jr. Lima 456");
        e1.setDni(11112222);
        e1.setTipoEntrega("RECOJO");

        Entrega e2 = new Entrega();
        e2.setIdEntrega(2);
        e2.setId_orden(2002);
        e2.setFecha_entrega(Timestamp.valueOf("2025-05-17 15:00:00"));
        e2.setDireccion("Av. Perú 789");
        e2.setDni(22223333);
        e2.setTipoEntrega("DELIVERY");

        entregaDAO.agregar(e1);
        entregaDAO.agregar(e2);

        List<Entrega> entregas = entregaDAO.listarTodos();
        assertTrue(entregas.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Entrega entrega = new Entrega();
        entrega.setIdEntrega(3);
        entrega.setId_orden(3001);
        entrega.setFecha_entrega(Timestamp.valueOf("2025-05-18 08:00:00"));
        entrega.setDireccion("Calle Falsa 123");
        entrega.setDni(33334444);
        entrega.setTipoEntrega("RECOJO");

        entregaDAO.agregar(entrega);

        entrega.setTipoEntrega("DELIVERY");
        entregaDAO.actualizar(entrega);

        Entrega actualizado = entregaDAO.obtener(3);
        assertEquals("DELIVERY", actualizado.getTipoEntrega());
    }

    @Test
    public void testEliminar() {
        Entrega entrega = new Entrega();
        entrega.setIdEntrega(4);
        entrega.setId_orden(4001);
        entrega.setFecha_entrega(Timestamp.valueOf("2025-05-19 17:00:00"));
        entrega.setDireccion("Av. Ejemplo 321");
        entrega.setDni(44445555);
        entrega.setTipoEntrega("DELIVERY");

        entregaDAO.agregar(entrega);
        entregaDAO.eliminar(4);

        assertNull(entregaDAO.obtener(4));
    }
}
