package com.jmq.inversiones.business.impl.ventas;

import com.jmq.inversiones.business.impl.ventas.ProductoServiceImpl;
import com.jmq.inversiones.business.ventas.OrdenVentaService;
import com.jmq.inversiones.business.ventas.ProductoService;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import com.jmq.inversiones.jmqpersistencia.dao.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdenVentaServiceImpl implements OrdenVentaService{

    private final OrdenVentaDAO ordenVentaDAO;
    private final DetalleDAO detallesDAO;
    private final ProductoService productoService;
    public OrdenVentaServiceImpl(OrdenVentaDAO ordenVentaDAO, DetalleDAO detalleDao) {
        this.ordenVentaDAO = ordenVentaDAO;
        this.detallesDAO = detalleDao;
        this.productoService = new ProductoServiceImpl(new ProductoDAOImpl());
    }

    @Override
    public void registrarOrdenVenta(OrdenVenta ordenVenta) throws Exception {
        try {
            // Validaciones básicas
            if (ordenVenta.getUsuario() == null) {
                throw new Exception("El usuario es requerido");
            }
            if (ordenVenta.getDetalle() == null || ordenVenta.getDetalle().isEmpty()) {
                throw new Exception("La orden debe tener al menos un detalle");
            }
            
            // Establecer valores por defecto
            if (ordenVenta.getFecha_orden() == null) {
                ordenVenta.setFecha_orden(new Date()); // Fecha actual por defecto
            }
            if (ordenVenta.getEstado_compra() == null) {
                ordenVenta.setEstado_compra(EstadoCompra.pendiente); // Estado por defecto
            }
            
            // Validar stock de productos en los detalles
            validarStockProductos(ordenVenta.getDetalle());
            
            // Llamar al DAO
            ordenVentaDAO.agregar(ordenVenta);
            //Efectuar for para agregar Detalles en la BD
            
            for(Detalle detalle: ordenVenta.getDetalle()){
                detalle.setOrden(ordenVenta);
                detallesDAO.agregar(detalle);
            }
            // Actualizar stock de productos (dependiendo de tu lógica de negocio)
            actualizarStockProductos(ordenVenta.getDetalle(), false);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar orden de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarOrdenVenta(OrdenVenta ordenVenta) throws Exception {
        try {
            // Validaciones
            if (ordenVenta.getId() <= 0) {
                throw new Exception("ID de orden inválido");
            }
            if (ordenVenta.getUsuario() == null) {
                throw new Exception("El usuario es requerido");
            }
            
            // Llamar al DAO
            ordenVentaDAO.actualizar(ordenVenta);
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar orden de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarOrdenVenta(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de orden inválido");
            }
            
            // Obtener la orden para verificar detalles antes de eliminar
            OrdenVenta orden = ordenVentaDAO.obtener(id);
            if (orden == null) {
                throw new Exception("Orden no encontrada");
            }
            
            // Si la orden ya está completada, no se puede eliminar
            if (orden.getEstado_compra() == EstadoCompra.entregado) {
                throw new Exception("No se puede eliminar una orden completada");
            }
            
            // Devolver stock si es necesario
            List<Detalle> detalles = orden.getDetalle();
            if (detalles != null && !detalles.isEmpty()) {
                actualizarStockProductos(detalles, true);
            }

            
            // Llamar al DAO
            ordenVentaDAO.eliminar(id);
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar orden de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public OrdenVenta buscarOrdenVenta(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de orden inválido");
            }
            
            // Llamar al DAO
            return ordenVentaDAO.obtener(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar orden de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OrdenVenta> listarOrdenVentas() throws Exception {
        try {
            // Llamar al DAO
            return ordenVentaDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar órdenes de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarEstado(int id, String estado) throws Exception {
        try {
            // Validaciones
            if (id <= 0) {
                throw new Exception("ID de orden inválido");
            }
            if (estado == null || estado.isEmpty()) {
                throw new Exception("El estado es requerido");
            }
            
            // Obtener la orden actual
            OrdenVenta orden = ordenVentaDAO.obtener(id);
            if (orden == null) {
                throw new Exception("Orden no encontrada");
            }
            
            // Convertir String a Enum
            EstadoCompra nuevoEstado;
            try {
                nuevoEstado = EstadoCompra.valueOf(estado.toLowerCase());
            } catch (IllegalArgumentException e) {
                throw new Exception("Estado no válido: " + estado);
            }
            
            // Validar transición de estado
            validarTransicionEstado(orden.getEstado_compra(), nuevoEstado);
            
            // Actualizar estado
            orden.setEstado_compra(nuevoEstado);
            ordenVentaDAO.actualizar(orden);
            
            // Lógica adicional según cambio de estado
            if (nuevoEstado == EstadoCompra.entregado) {
            }
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar estado de orden: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void agregarDetalle(OrdenVenta ordenVenta,Detalle detalle) throws Exception{
        try {
            if (ordenVenta.getDetalle() == null) {
                ordenVenta.setDetalle(new ArrayList<>());
            }
            if(detalle.getCantidad() < 1 ){
                throw new Exception("La cantidad debe ser mayor 1");
            }
            ordenVenta.getDetalle().add(detalle);
        } catch (Exception e) {
            throw new Exception("Error al agregar detalle a orden: " + e.getMessage(), e);
        }
    }
    @Override
    public void eliminarDetalle(OrdenVenta ordenVenta, Detalle detalle) throws Exception{
         try {
            Iterator<Detalle> iterator = ordenVenta.getDetalle().iterator();
            while (iterator.hasNext()) {
                Detalle aux = iterator.next();
                if (aux.getProducto().getId() == detalle.getProducto().getId()) {
                    iterator.remove();
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al elimnar detalle a orden: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<OrdenVenta> listarOrdenVentaByUsuario(int id_usuario) throws Exception{
         try {
             List<OrdenVenta> lista = ordenVentaDAO.listarPorUsuario(id_usuario);
            if(lista == null){
                 throw new Exception("El usuario no cuenta con ninguna OrdenVenta");
            }
            return lista;
        } catch (Exception e) {
            throw new Exception("Error al elimnar detalle a orden: " + e.getMessage(), e);
        }
    }
    // Métodos auxiliares privados

    private void validarStockProductos(List<Detalle> detalles) throws Exception {
        //  validar que hay suficiente stock para cada producto en los detalles de la orden
        for (Detalle detalle : detalles) {
            if (detalle.getProducto().getStock() < detalle.getCantidad()) {
                throw new Exception("Stock insuficiente para el producto: " + 
                        detalle.getProducto().getNombre());
            }
        }
    }

    private void actualizarStockProductos(List<Detalle> detalles, boolean revertir) throws Exception {
        // actualizar el stock de productos
        // Si revertir es true, se suma el stock (para cancelaciones)
        // Si revertir es false, se resta el stock (para nuevas órdenes)
        int factor = revertir ? 1 : -1;
        for (Detalle detalle : detalles) {
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();
            if (!revertir && producto.getStock() < cantidad) {
                throw new Exception(
                    String.format("Stock insuficiente para el producto '%s'. Stock actual: %d, Se requieren: %d",producto.getNombre(), producto.getStock(), cantidad)
                );
            }
            producto.setStock(producto.getStock() + (factor * detalle.getCantidad()));
            productoService.actualizarProducto(producto);
        }
    }

    private void validarTransicionEstado(EstadoCompra estadoActual, EstadoCompra nuevoEstado) throws Exception {
        if (estadoActual == EstadoCompra.entregado && nuevoEstado != EstadoCompra.entregado) {
            throw new Exception("No se puede modificar una orden completada");
        }
        if (estadoActual == EstadoCompra.cancelado && nuevoEstado != EstadoCompra.cancelado) {
            throw new Exception("No se puede modificar una orden cancelada");
        }
    }
    @Override
    public List<OrdenVenta> filtrarOrdenesVenta(String estadoCompra, Boolean activo, Integer idUsuario,
                                           String fechaDesde, String fechaHasta) throws SQLException{
        try {
             List<OrdenVenta> lista = ordenVentaDAO.filtrarOrdenesVenta(estadoCompra, activo, idUsuario, fechaDesde, fechaHasta);
            return lista;
        } catch (SQLException e) {
     
            throw new SQLException("Error al elimnar detalle a orden: " + e.getMessage(), e);
            
        }
    }
}
