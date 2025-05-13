package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Boleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testBoletaDAO {

    private BoletaDAO boletaDAO;

    @BeforeEach
    public void setUp() {
        boletaDAO = new BoletaDAO(); // Asegúrate que esté implementado correctamente
    }

    @Test
    public void testAgregarYObtener() {
        Boleta bol = new Boleta();
        bol.setIdBoleta(1); // es también idComprobantePago
        bol.setDni("12345678");
        bol.setNombre("Cliente Ejemplo");
        bol.setFecha_emision(Timestamp.valueOf("2025-05-13 11:00:00"));

        boletaDAO.agregar(bol);
        Boleta obtenido = boletaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Cliente Ejemplo", obtenido.getNombre());
    }

    @Test
    public void testListarTodos() {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        b1.setDni("87654321");
        b1.setNombre("Cliente A");
        b1.setFecha_emision(Timestamp.valueOf("2025-05-13 12:00:00"));

        Boleta b2 = new Boleta();
        b2.setIdBoleta(2);
        b2.setDni("11223344");
        b2.setNombre("Cliente B");
        b2.setFecha_emision(Timestamp.valueOf("2025-05-13 13:00:00"));

        boletaDAO.agregar(b1);
        boletaDAO.agregar(b2);

        List<Boleta> boletas = boletaDAO.listarTodos();
        assertTrue(boletas.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Boleta bol = new Boleta();
        bol.setIdBoleta(3);
        bol.setDni("00000000");
        bol.setNombre("Temporal");
        bol.setFecha_emision(Timestamp.valueOf("2025-05-13 14:00:00"));

        boletaDAO.agregar(bol);

        bol.setNombre("Nombre Actualizado");
        boletaDAO.actualizar(bol);

        Boleta actualizado = boletaDAO.obtener(3);
        assertEquals("Nombre Actualizado", actualizado.getNombre());
    }

    @Test
    public void testEliminar() {
        Boleta bol = new Boleta();
        bol.setIdBoleta(4);
        bol.setDni("99999999");
        bol.setNombre("Eliminar");
        bol.setFecha_emision(Timestamp.valueOf("2025-05-13 15:00:00"));

        boletaDAO.agregar(bol);
        boletaDAO.eliminar(4);

        assertNull(boletaDAO.obtener(4));
    }
}
