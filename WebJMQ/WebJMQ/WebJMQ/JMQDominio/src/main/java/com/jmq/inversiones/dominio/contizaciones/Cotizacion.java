package com.jmq.inversiones.dominio.contizaciones;

import com.jmq.inversiones.dominio.usuario.Usuario;
import java.util.List;

public class Cotizacion {
    private int id;
    private Usuario usuario;
    private boolean activo;
    private String estadoCotizacion;
    private List<ProductoCotizacion> productos;
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Cotizacion() {
    }

    public Cotizacion(int id, Usuario usuario, String estadoCotizacion) {
        this.id = id;
        this.usuario = usuario;
        this.estadoCotizacion = estadoCotizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstadoCotizacion() {
        return estadoCotizacion;
    }

    public void setEstadoCotizacion(String estadoCotizacion) {
        this.estadoCotizacion = estadoCotizacion;
    }

    @Override
    public String toString() {
        return "Cotizacion{" + "id=" + id + ", usuario=" + usuario + ", estadoCotizacion=" + estadoCotizacion + '}';
    }

    /**
     * @return the productos
     */
    public List<ProductoCotizacion> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<ProductoCotizacion> productos) {
        this.productos = productos;
    }

    
    
}
