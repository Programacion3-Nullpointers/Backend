package com.jmq.inversiones.jmqpersistencia.test.pagos;

import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.pagos.FacturaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.pagos.FacturaDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.OrdenVentaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTest {

    private FacturaDAO facturaDAO;

    @BeforeEach
    public void setUp() {
        facturaDAO = new FacturaDAOImpl();
    }

    @Test
    public void testAgregarYObtener() {
        Factura factura = crearFacturaEjemplo();
        facturaDAO.agregar(factura);

        Factura obtenido = facturaDAO.obtener(factura.getId());

        assertNotNull(obtenido);
        assertEquals("Empresa SAC", obtenido.getRazon_social());
        assertEquals("12345678901", obtenido.getRUC());
    }

    @Test
    public void testActualizar() {
        Factura factura = crearFacturaEjemplo();
        facturaDAO.agregar(factura);

        factura.setRazon_social("Empresa Actualizada");
        factura.setMonto_total(999.99);
        facturaDAO.actualizar(factura);

        Factura actualizado = facturaDAO.obtener(factura.getId());
        assertEquals("Empresa Actualizada", actualizado.getRazon_social());
        assertEquals(999.99, actualizado.getMonto_total());
    }

    @Test
    public void testEliminar() {
//        Factura factura = crearFacturaEjemplo(3);
//        facturaDAO.agregar(factura);

        facturaDAO.eliminar(69);
        Factura eliminado = facturaDAO.obtener(69);

        assertNull(eliminado);
    }

    private Factura crearFacturaEjemplo() {
        OrdenVenta orden = new OrdenVenta();
        orden.setEstado_compra(EstadoCompra.pagado);
        orden.setFecha_orden(new Date());
        orden.setActivo(true);

        Usuario u = new Usuario();
        u.setId(1); // Asegúrate que este ID existe
        orden.setUsuario(u);

        new OrdenVentaDAOImpl().agregar(orden); // guarda la orden

        Factura f = new Factura();
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
