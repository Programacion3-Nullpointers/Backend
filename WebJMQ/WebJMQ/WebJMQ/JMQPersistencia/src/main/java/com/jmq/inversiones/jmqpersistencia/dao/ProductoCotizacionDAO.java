package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import java.util.List;


public interface ProductoCotizacionDAO extends BaseDAO<ProductoCotizacion>{
    void actualizarPrecioCotizacion(ProductoCotizacion pro, double precio);
    List<ProductoCotizacion> listarPorCotizacion(int idCotizacion);
}
