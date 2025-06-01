package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.pagos.Boleta;
import java.util.List;

public interface BoletaService {
    void registrarBoleta(Boleta boleta) throws Exception;
    void actualizarBoleta(Boleta boleta) throws Exception;
    void eliminarBoleta(int id) throws Exception;
    Boleta buscarBoleta(int id) throws Exception;
    List<Boleta> listarBoletas() throws Exception;
    
}
