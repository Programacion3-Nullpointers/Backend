package com.jmq.inversiones.jmqpersistencia.dao.cotizaciones;


import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAO;

import java.util.List;


public interface ProductoCotizacionDAO extends BaseDAO<ProductoCotizacion>{
    void actualizarPrecioCotizacion(ProductoCotizacion pro, double precio);
    List<ProductoCotizacion> listarPorCotizacion(int idCotizacion);
}
