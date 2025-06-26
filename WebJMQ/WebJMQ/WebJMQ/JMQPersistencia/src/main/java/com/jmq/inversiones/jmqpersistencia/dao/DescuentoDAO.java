package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.pagos.Descuento;
import java.sql.SQLException;
import java.util.List;


public interface DescuentoDAO extends BaseDAO<Descuento>{
     List<Descuento> filtrarDescuentos(Boolean activo, Integer porcentajeMin, Integer porcentajeMax) throws SQLException;
     void activarDescuento(int idDescuento) throws Exception;
     void desactivarDescuento(int idDescuento) throws Exception;
}
