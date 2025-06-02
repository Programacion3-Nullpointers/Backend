package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ComprobantePagoDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ComprobantePagoMySQL {

    private ComprobantePagoDAOImpl dao;

    @BeforeEach
    public void setUp() {
        dao = new ComprobantePagoDAOImpl() {}; // Si es una clase abstracta, puede usarse así
    }

    @Test
    public void testAgregarYObtener() {
        ComprobantePago cp = crearComprobantePagoEjemplo(6);
        dao.agregar(cp);

        ComprobantePago obtenido = dao.obtener(cp.getId());
        assertNotNull(obtenido);
        assertEquals(cp.getMetodoPago(), obtenido.getMetodoPago());
        assertEquals(cp.getMonto_total(), obtenido.getMonto_total());
    }

    @Test
    public void testActualizar() {
        ComprobantePago cp = dao.obtener(51);

        cp.setMonto_total(999.99);
        dao.actualizar(cp);

        ComprobantePago actualizado = dao.obtener(51);
        assertEquals(999.99, actualizado.getMonto_total());
    }

    @Test
    public void testEliminar() {
//        ComprobantePago cp = crearComprobantePagoEjemplo(3);
//        dao.agregar(cp);

        dao.eliminar(64);
        assertNull(dao.obtener(64));
    }

    private ComprobantePago crearComprobantePagoEjemplo(int id) {
        OrdenVenta orden = new OrdenVenta();
        orden.setId(id); // dummy orden

        ComprobantePago cp = new ComprobantePago();
//        cp.setId(id);
        cp.setOrden(orden);
        cp.setMetodoPago(MetodoPago.efectivo);
        cp.setFecha_pago(new Date());
        cp.setMonto_total(500.0*id);
        return cp;
    }
}
