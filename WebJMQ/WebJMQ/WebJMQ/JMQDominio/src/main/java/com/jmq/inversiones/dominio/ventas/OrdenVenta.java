package com.jmq.inversiones.dominio.ventas;

import com.jmq.inversiones.dominio.usuario.Usuario;
import java.util.Date;
import java.util.List;

public class OrdenVenta {
    private int id;
    private EstadoCompra estado_compra;
    private Date fecha_orden;
    private boolean activo;
    private Usuario usuario;
    private List<Detalle>detalles;
    public OrdenVenta(){
        
    }

    public OrdenVenta(int id, EstadoCompra estado_compra, Date fecha_orden, Usuario usuario) {
        this.id = id;
        this.estado_compra = estado_compra;
        this.fecha_orden = fecha_orden;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoCompra getEstado_compra() {
        return estado_compra;
    }

    public void setEstado_compra(EstadoCompra estado_compra) {
        this.estado_compra = estado_compra;
    }

    public Date getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(Date fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "OrdenVenta{" + "id=" + id + ", estado_compra=" + estado_compra + ", fecha_orden=" + fecha_orden + ", usuario=" + usuario + '}';
    }

    /**
     * @return the detalle
     */
    public List<Detalle> getDetalle() {
        return detalles;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(List<Detalle> detalle) {
        this.detalles = detalle;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
