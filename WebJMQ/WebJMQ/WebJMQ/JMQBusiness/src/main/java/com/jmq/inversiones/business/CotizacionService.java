package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import java.util.List;

public interface CotizacionService {
    void registrarCotizacion(Cotizacion c) throws Exception;
    void actualizarCotizacion(Cotizacion c) throws Exception;
    void eliminarCotizacion(int id) throws Exception;
    Cotizacion buscarCotizacion(int id) throws Exception;
    List<Cotizacion> listarCotizaciones() throws Exception;
    //List<Cotizacion> listarPorUsuario(int id) throws Exception;

}
