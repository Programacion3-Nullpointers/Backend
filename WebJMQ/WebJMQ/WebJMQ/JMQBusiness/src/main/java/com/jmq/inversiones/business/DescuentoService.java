package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.pagos.Descuento;
import java.util.List;

public interface DescuentoService {
    void registrarDescuento(Descuento descuento) throws Exception;
    void actualizarDescuento(Descuento descuento) throws Exception;
    void eliminarDescuento(int id) throws Exception;
    Descuento buscarDescuento(int id) throws Exception;
    List<Descuento> listarDescuentos() throws Exception;
 
}

