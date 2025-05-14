package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.DetalleDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDetalleDAO {

    private DetalleDAO detalleDAO;

    @BeforeEach
    public void setUp() {
        detalleDAO = new DetalleDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Detalle detalle = crearDetalleEjemplo(1, 1);
        detalleDAO.agregar(detalle);

        List<Detalle> lista = detalleDAO.listarTodos();
        boolean encontrado = lista.stream()
                .anyMatch(d -> d.getId() == detalle.getId() && d.getProducto().getId() == detalle.getProducto().getId());

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Detalle detalle = crearDetalleEjemplo(2, 2);
        detalleDAO.agregar(detalle);

        detalle.setCantidad(10);
        detalle.setPrecio_unitario(55.0);
        detalleDAO.actualizar(detalle);

        List<Detalle> lista = detalleDAO.listarTodos();
        Detalle actualizado = lista.stream()
                .filter(d -> d.getId() == detalle.getId() && d.getProducto().getId() == detalle.getProducto().getId())
                .findFirst().orElse(null);

        assertNotNull(actualizado);
        assertEquals(10, actualizado.getCantidad());
        assertEquals(55.0, actualizado.getPrecio_unitario());
    }

    @Test
    public void testEliminar() {
        Detalle detalle = crearDetalleEjemplo(3, 3);
        detalleDAO.agregar(detalle);

        detalleDAO.eliminar(detalle.getId(), detalle.getProducto().getId());

        List<Detalle> lista = detalleDAO.listarTodos();
        boolean eliminado = lista.stream()
                .noneMatch(d -> d.getId() == detalle.getId() && d.getProducto().getId() == detalle.getProducto().getId());

        assertTrue(eliminado);
    }

    private Detalle crearDetalleEjemplo(int idOrden, int idProducto) {
        Producto prod = new Producto();
        prod.setId(idProducto); // ⚠️ Este producto debe existir

        Detalle detalle = new Detalle();
        detalle.setId(idOrden);  // id_orden
        detalle.setProducto(prod);
        detalle.setCantidad(5);
        detalle.setPrecio_unitario(25.0);
        return detalle;
    }
}
