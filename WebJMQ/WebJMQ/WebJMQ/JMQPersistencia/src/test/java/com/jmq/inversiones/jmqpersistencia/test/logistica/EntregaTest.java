package com.jmq.inversiones.jmqpersistencia.test.logistica;

import com.jmq.inversiones.dominio.logistica.Entrega;
import com.jmq.inversiones.dominio.logistica.TipoEntrega;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.logistica.EntregaDAO;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.logistica.EntregaDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.OrdenVentaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntregaTest {

    private EntregaDAO entregaDAO;
    private OrdenVentaDAO ordenVentaDAO;

    @BeforeEach
    public void setUp() {
        entregaDAO = new EntregaDAOImpl();
        ordenVentaDAO = new OrdenVentaDAOImpl();
    }

    private OrdenVenta crearOrdenDummy() {
        OrdenVenta orden = new OrdenVenta();
        orden.setEstado_compra(EstadoCompra.pendiente);
        orden.setFecha_orden(new Date());
        orden.setActivo(true);

        Usuario usuario = new Usuario();
        usuario.setId(1); // ⚠️ Este usuario DEBE existir en la base de datos
        orden.setUsuario(usuario);

        ordenVentaDAO.agregar(orden);
        return orden;
    }

    private Entrega crearEntregaEjemplo() {
        OrdenVenta orden = crearOrdenDummy(); // ✅ se asegura que la orden exista

        Entrega entrega = new Entrega();
        entrega.setOrden(orden);
        entrega.setFecha_entrega(new Date());
        entrega.setDireccion("Av. Test #" + orden.getId());
        entrega.setDniRecibo("12345678");
        entrega.setTipoEntrega(TipoEntrega.DELIVERY); // o TipoEntrega.RECOJO

        return entrega;
    }

    @Test
    public void testAgregarYListar() {
        Entrega entrega = crearEntregaEjemplo();
        entregaDAO.agregar(entrega);

        List<Entrega> entregas = entregaDAO.listarTodos();
        boolean encontrado = entregas.stream()
                .anyMatch(e -> e.getId() == entrega.getId());

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Entrega entrega = crearEntregaEjemplo();
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
        assertEquals("76543210", actualizado.getDniRecibo());
    }

    @Test
    public void testEliminar() {
        Entrega entrega = crearEntregaEjemplo();
        entregaDAO.agregar(entrega);

        entregaDAO.eliminar(entrega.getId());

        List<Entrega> entregas = entregaDAO.listarTodos();
        boolean eliminado = entregas.stream()
                .noneMatch(e -> e.getId() == entrega.getId());

        assertTrue(eliminado);
    }
}
