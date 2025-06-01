package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.FacturaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaMySQL {

    private FacturaDAO facturaDAO;

    @BeforeEach
    public void setUp() {
        facturaDAO = new FacturaDAOImpl();
    }

    @Test
    public void testAgregarYObtener() {
        Factura factura = crearFacturaEjemplo(1);
        facturaDAO.agregar(factura);

        Factura obtenido = facturaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Empresa SAC", obtenido.getRazon_social());
        assertEquals("12345678901", obtenido.getRUC());
    }

    @Test
    public void testActualizar() {
        Factura factura = crearFacturaEjemplo(2);
        facturaDAO.agregar(factura);

        factura.setRazon_social("Empresa Actualizada");
        factura.setMonto_total(999.99);
        facturaDAO.actualizar(factura);

        Factura actualizado = facturaDAO.obtener(2);
        assertEquals("Empresa Actualizada", actualizado.getRazon_social());
        assertEquals(999.99, actualizado.getMonto_total());
    }

    @Test
    public void testEliminar() {
        Factura factura = crearFacturaEjemplo(3);
        facturaDAO.agregar(factura);

        facturaDAO.eliminar(3);
        Factura eliminado = facturaDAO.obtener(3);

        assertNull(eliminado);
    }

    private Factura crearFacturaEjemplo(int id) {
        OrdenVenta orden = new OrdenVenta();
        orden.setId(100 + id); // Asume que esa orden existe

        Factura f = new Factura();
        f.setId(id);
        f.setOrden(orden);
        f.setMetodoPago(MetodoPago.tarjeta);
        f.setFecha_pago(new Date());
        f.setMonto_total(1500.00);
        f.setRUC("12345678901");
        f.setRazon_social("Empresa SAC");
        f.setDireccion("Av. Factura 123");
        f.setFecha_emision(new Date());
        return f;
    }
}
