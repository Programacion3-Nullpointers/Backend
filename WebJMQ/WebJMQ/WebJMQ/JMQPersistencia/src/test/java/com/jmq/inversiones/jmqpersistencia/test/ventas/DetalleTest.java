package com.jmq.inversiones.jmqpersistencia.test.ventas;

import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.DetalleDAO;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.ProductoDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.DetalleDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.OrdenVentaDAOImpl;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.ProductoDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleTest {

    private DetalleDAO detalleDAO;
    private ProductoDAO productoDAO;
    private OrdenVentaDAO ordenVentaDAO;

    @BeforeEach
    public void setUp() {
        detalleDAO = new DetalleDAOImpl();
        productoDAO = new ProductoDAOImpl();
        ordenVentaDAO = new OrdenVentaDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Detalle detalle = crearDetalleEjemplo();
        detalleDAO.agregar(detalle);

        List<Detalle> lista = detalleDAO.listarTodos();
        boolean encontrado = lista.stream()
                .anyMatch(d ->
                    d.getOrden() != null && d.getProducto() != null &&
                    d.getOrden().getId() == detalle.getOrden().getId() &&
                    d.getProducto().getId() == detalle.getProducto().getId());

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Detalle detalle = crearDetalleEjemplo();
        detalleDAO.agregar(detalle);

        detalle.setCantidad(10);
        detalle.setPrecio_unitario(99.99);
        detalleDAO.actualizar(detalle);

        Detalle actualizado = detalleDAO.obtener(
                detalle.getOrden().getId(),
                detalle.getProducto().getId()
        );
        assertNotNull(actualizado);
        assertEquals(10, actualizado.getCantidad());
        assertEquals(99.99, actualizado.getPrecio_unitario(), 0.01);
    }

    @Test
    public void testEliminar() {
        Detalle detalle = crearDetalleEjemplo();
        detalleDAO.agregar(detalle);

        detalleDAO.eliminar(
                detalle.getOrden().getId(),
                detalle.getProducto().getId()
        );

        Detalle eliminado = detalleDAO.obtener(
                detalle.getOrden().getId(),
                detalle.getProducto().getId()
        );
        assertNull(eliminado);
    }
    
    @Test
    public void testListarPorOrden() {
        // Arrange
        int idOrdenExistente = 32;
         Detalle detalle = detalleDAO.obtener(idOrdenExistente,4);
        assertNotNull(detalle, "El detalle con idOrden=32 y idProducto=4 no existe. Asegúrate de que la BD esté preparada para este test.");

        // Act
        List<Detalle> lista = detalleDAO.listarPorOrden(idOrdenExistente);

        boolean encontrado = lista.stream()
                .anyMatch(d ->
                    d.getOrden() != null && d.getProducto() != null &&
                    d.getOrden().getId() == detalle.getOrden().getId() &&
                    d.getProducto().getId() == detalle.getProducto().getId());

        assertTrue(encontrado);
        
        
    }

    // ✅ Utiliza una orden activa y un producto existente
    private Detalle crearDetalleEjemplo() {
        // Obtener producto existente
        Producto producto = productoDAO.listarTodos().stream().findFirst().orElse(null);
        assertNotNull(producto, "Debe existir al menos un producto en la base de datos");

        // Crear orden con usuario válido
        Usuario usuario = new Usuario();
        usuario.setId(1); 

        OrdenVenta orden = new OrdenVenta();
        orden.setUsuario(usuario);
        orden.setEstado_compra(EstadoCompra.pendiente);
        orden.setFecha_orden(new Date());
        orden.setActivo(true);
        ordenVentaDAO.agregar(orden);

        // Crear y retornar el detalle
        Detalle detalle = new Detalle();
        detalle.setOrden(orden);
        detalle.setProducto(producto);
        detalle.setCantidad(3);
        detalle.setPrecio_unitario(producto.getPrecio());
        return detalle;
    }
}
