package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;


public interface ProductoCotizacionDAO extends BaseDAO<ProductoCotizacion>{
    void actualizarPrecioCotizacion(Integer id,Integer fid, double precio);
}
