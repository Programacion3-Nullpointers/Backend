
package com.jmq.inversiones.business.cotizaciones;

import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import java.util.List;


public interface ProductoCotizacionService {
    void registrarProductoCotizacion(ProductoCotizacion prod) throws Exception;
    void actualizarProductoCotizacion(ProductoCotizacion prod) throws Exception;
    void eliminarProductoCotizacion(int id) throws Exception;
    ProductoCotizacion buscarProductoCotizacion(int id) throws Exception;
    List<ProductoCotizacion> listarProductoCotizacion() throws Exception;
    List<ProductoCotizacion> listarProductPorCotizacion(int id) throws Exception;
    void actualizarPrecioCotizacion(ProductoCotizacion prod,double precio) throws Exception;
}
