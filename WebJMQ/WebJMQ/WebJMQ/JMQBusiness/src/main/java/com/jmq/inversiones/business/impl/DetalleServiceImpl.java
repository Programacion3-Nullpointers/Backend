/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.DetalleService;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import java.util.List;

public class DetalleServiceImpl implements DetalleService{
    
    private final DetalleDAO detalleDAO;
    
    public DetalleServiceImpl(DetalleDAO detalleDAO){
        this.detalleDAO = detalleDAO;
    }

    @Override
    public void registrarDetalle(Detalle detalle) throws Exception {
        try {
            validarDetalle(detalle);
            
            detalleDAO.agregar(detalle);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar detalle: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarDetalle(Detalle detalle) throws Exception {
        try {
            validarDetalle(detalle);
            if (detalleDAO.obtener(detalle.getProducto().getId(),detalle.getOrden().getId()) == null) {
                throw new Exception("Detalle no encontrado");
            }
            detalleDAO.actualizar(detalle);
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar detalle: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarDetalle(int idOrden, int idProducto) throws Exception {
        try {
            if (idOrden <= 0 || idProducto <= 0) {
                throw new Exception("IDs de orden y/o producto inválidas");
            }
            
            detalleDAO.eliminar(idOrden, idProducto);
        } catch (Exception e) {
            throw new Exception("Error al eliminar detalle: " + e.getMessage(), e);
        }
    }

    @Override
    public Detalle buscarDetalle(int idOrden, int idProducto) throws Exception {
        try {
            if (idOrden <= 0 || idProducto <= 0) {
                throw new Exception("IDs de orden y/o producto inválidas");
            }
            
            Detalle detalle = detalleDAO.obtener(idOrden, idProducto);
            if (detalle == null) {
                throw new Exception("Detalle no encontrado");
            }
            
            return detalle;
        } catch (Exception e) {
            throw new Exception("Error al buscar detalle: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Detalle> listarDetalles() throws Exception {
        try {
            return detalleDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar detalles: " + e.getMessage(), e);
        }
    }
    
    private void validarDetalle(Detalle detalle) throws Exception {
        if (detalle.getCantidad()<= 0 || detalle.getPrecio_unitario()<= 0) {
            throw new Exception("Los valores de cantidad o precio unitario son inválidos.");
        }
        if (detalle.getProducto() == null){
            throw new Exception("El detalle debe incluir un producto.");
        }
        if (detalle.getOrden()== null){
            throw new Exception("El detalle debe estar asociado a una orden de venta.");
        }
        if (detalle.getProducto().getId() <= 0 || detalle.getOrden().getId() <= 0){
            throw new Exception("Las IDs de producto y/o orden de venta son inválidas.");
        }
    }
    
    @Override
    public List<Detalle> listarPorOrden(int idOrden) throws Exception{
        try {
            return detalleDAO.listarPorOrden(idOrden);
        } catch (Exception e) {
            throw new Exception("Error al listar detalles por orden: " + e.getMessage(), e);
        }
    }
}
