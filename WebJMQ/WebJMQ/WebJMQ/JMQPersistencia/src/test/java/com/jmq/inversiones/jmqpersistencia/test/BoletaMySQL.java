package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.BoletaDAOImpl;
import com.jmq.inversiones.dominio.pagos.Boleta;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoletaMySQL {

    private BoletaDAO boletaDAO;

    @BeforeEach
    public void setUp() {
        boletaDAO = new BoletaDAOImpl(); // Asegúrate que este DAO se conecta o está simulado correctamente
    }

    @Test
    public void testAgregarYObtener() {
        Boleta bol = crearBoletaEjemplo(1);

        boletaDAO.agregar(bol);
        Boleta obtenido = boletaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Cliente Test", obtenido.getNombre());
        assertEquals("12345678", obtenido.getDni());
    }

    @Test
    public void testListarTodos() {
        boletaDAO.agregar(crearBoletaEjemplo(2));
        boletaDAO.agregar(crearBoletaEjemplo(3));

        List<Boleta> boletas = boletaDAO.listarTodos();
        assertTrue(boletas.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Boleta bol = crearBoletaEjemplo(4);
        boletaDAO.agregar(bol);

        bol.setNombre("Cliente Actualizado");
        boletaDAO.actualizar(bol);

        Boleta actualizado = boletaDAO.obtener(4);
        assertEquals("Cliente Actualizado", actualizado.getNombre());
    }

    @Test
    public void testEliminar() {
        Boleta bol = crearBoletaEjemplo(5);
        boletaDAO.agregar(bol);

        boletaDAO.eliminar(5);
        assertNull(boletaDAO.obtener(5));
    }

    private Boleta crearBoletaEjemplo(int id) {
        OrdenVenta ordenDummy = new OrdenVenta();
        ordenDummy.setId(1000 + id); // solo para referencia interna

        Boleta boleta = new Boleta();
        boleta.setId(id);
        boleta.setOrden(ordenDummy);
        boleta.setMetodoPago(MetodoPago.tarjeta); // Asumiendo que es un enum
        boleta.setFecha_pago(new Date());
        boleta.setMonto_total(250.0);
        boleta.setDni("12345678");
        boleta.setNombre("Cliente Test");
        boleta.setFecha_emision(new Date());
        return boleta;
    }
}
