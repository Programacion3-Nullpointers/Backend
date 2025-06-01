package com.jmq.inversiones.dominio.logistica;

import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.Date;

public class Entrega {
    private int id;
    private OrdenVenta orden;
    private Date fecha_entrega;
    private String direccion;
    private String dni_recibo;
    private TipoEntrega tipoEntrega;

    public Entrega() {
    }

    public Entrega(int id, OrdenVenta orden, Date fecha_entrega, String direccion, String dni, TipoEntrega tipoEntrega) {
        this.id = id;
        this.orden = orden;
        this.fecha_entrega = fecha_entrega;
        this.direccion = direccion;
        this.dni_recibo = dni;
        this.tipoEntrega = tipoEntrega;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrdenVenta getOrden() {
        return orden;
    }

    public void setOrden(OrdenVenta orden) {
        this.orden = orden;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDniRecibo() {
        return dni_recibo;
    }

    public void setDniRecibo(String dni) {
        this.dni_recibo = dni;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    @Override
    public String toString() {
        return "Entrega{" + "id=" + id + ", orden=" + orden + ", fecha_entrega=" + fecha_entrega + ", direccion=" + direccion + ", dni=" + dni_recibo + ", tipoEntrega=" + tipoEntrega + '}';
    }
    
    
}
