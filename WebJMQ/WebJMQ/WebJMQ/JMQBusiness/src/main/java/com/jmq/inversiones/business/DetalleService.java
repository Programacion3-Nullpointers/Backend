/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.ventas.Detalle;
import java.util.List;

public interface DetalleService {
    void registrarDetalle(Detalle detalle) throws Exception;
    void actualizarDetalle(Detalle detalle) throws Exception;
    void eliminarDetalle(int idOrden, int idProducto) throws Exception;
    Detalle buscarDetalle(int idOrden, int idProducto) throws Exception;
    List<Detalle> listarDetalles() throws Exception;
    List<Detalle> listarPorOrden(int idOrden) throws Exception;
}
