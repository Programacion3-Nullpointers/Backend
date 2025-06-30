package com.jmq.inversiones.business.pagos;

import com.jmq.inversiones.dominio.pagos.Descuento;
import java.sql.SQLException;
import java.util.List;

public interface DescuentoService {
    void registrarDescuento(Descuento descuento) throws Exception;
    void actualizarDescuento(Descuento descuento) throws Exception;
    void eliminarDescuento(int id) throws Exception;
    Descuento buscarDescuento(int id) throws Exception;
    List<Descuento> listarDescuentos() throws Exception;
    List<Descuento> filtrarDescuentos(Boolean activo, Integer porcentajeMin, Integer porcentajeMax) throws SQLException;
    void activarDescuento(int idDescuento) throws Exception;
    void desactivarDescuento(int idDescuento) throws Exception;
}
