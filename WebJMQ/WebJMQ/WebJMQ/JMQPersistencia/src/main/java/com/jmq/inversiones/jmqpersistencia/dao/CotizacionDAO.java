package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import java.util.List;

public interface CotizacionDAO extends BaseDAO<Cotizacion>{
    void actualizarEstado(int id,String estado);
    List<Cotizacion> obtenerPorUsuario(int idUsuario);
}
