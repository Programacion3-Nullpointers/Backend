package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.CotizacionDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Cotizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testCotizacionDAO {

    private CotizacionDAO cotizacionDAO;

    @BeforeEach
    public void setUp() {
        cotizacionDAO = new CotizacionDAO(); // Implementación requerida
    }

    @Test
    public void testAgregarYObtener() {
        Cotizacion cot = new Cotizacion();
        cot.setIdCotizacion(1);
        cot.setIdUsuario(1);
        cot.setEstadoCotizacion("En proceso");

        cotizacionDAO.agregar(cot);
        Cotizacion obtenido = cotizacionDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("En proceso", obtenido.getEstadoCotizacion());
    }

    @Test
    public void testListarTodos() {
        Cotizacion c1 = new Cotizacion();
        c1.setIdCotizacion(1);
        c1.setIdUsuario(2);
        c1.setEstadoCotizacion("En proceso");

        Cotizacion c2 = new Cotizacion();
        c2.setIdCotizacion(2);
        c2.setIdUsuario(3);
        c2.setEstadoCotizacion("Revisado");

        cotizacionDAO.agregar(c1);
        cotizacionDAO.agregar(c2);

        List<Cotizacion> cotizaciones = cotizacionDAO.listarTodos();
        assertTrue(cotizaciones.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Cotizacion cot = new Cotizacion();
        cot.setIdCotizacion(3);
        cot.setIdUsuario(1);
        cot.setEstadoCotizacion("En proceso");

        cotizacionDAO.agregar(cot);

        cot.setEstadoCotizacion("Revisado");
        cotizacionDAO.actualizar(cot);

        Cotizacion actualizado = cotizacionDAO.obtener(3);
        assertEquals("Revisado", actualizado.getEstadoCotizacion());
    }

    @Test
    public void testEliminar() {
        Cotizacion cot = new Cotizacion();
        cot.setIdCotizacion(4);
        cot.setIdUsuario(4);
        cot.setEstadoCotizacion("Eliminar");

        cotizacionDAO.agregar(cot);
        cotizacionDAO.eliminar(4);

        assertNull(cotizacionDAO.obtener(4));
    }
}

