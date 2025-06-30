package com.jmq.inversiones.business.logistica;

import com.jmq.inversiones.dominio.logistica.Entrega;
import java.util.List;

public interface EntregaService {
    void registrarEntrega(Entrega entrega) throws Exception;
    void actualizarEntrega(Entrega entrega) throws Exception;
    void eliminarEntrega(int id) throws Exception;
    Entrega buscarEntrega(int id) throws Exception;
//    Entrega buscarEntregaPorOrden(int Idorden) throws Exception;
    List<Entrega> listarEntrega() throws Exception;
 
}
