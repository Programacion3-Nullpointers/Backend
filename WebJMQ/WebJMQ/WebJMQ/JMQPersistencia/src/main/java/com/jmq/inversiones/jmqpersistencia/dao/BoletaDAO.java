package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.pagos.Boleta;


public interface BoletaDAO extends BaseDAO<Boleta>{
    void agregarHeredado(Boleta entity);
    void eliminarHeredado(int id);
}
