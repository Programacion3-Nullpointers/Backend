package com.jmq.inversiones.dominio.pagos;

import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.Date;

public abstract class ComprobantePago {
    protected int id;
    protected OrdenVenta orden;
    protected MetodoPago metodoPago;
    protected Date fecha_pago;
    protected double monto_total;

    public ComprobantePago(){
       // orden = new OrdenVenta();
        
    }

    public ComprobantePago(int id, OrdenVenta orden, MetodoPago metodoPago, Date fecha_pago, double monto_total) {
        this.id = id;
        this.orden = orden;
        this.metodoPago = metodoPago;
        this.fecha_pago = fecha_pago;
        this.monto_total = monto_total;
    }

    public ComprobantePago(ComprobantePago otro) {
        this.id = otro.id;
        this.orden = otro.orden;
        this.metodoPago = otro.metodoPago;
        this.fecha_pago = otro.fecha_pago;
        this.monto_total = otro.monto_total;
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

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    @Override
    public String toString() {
        return "ComprobantePago{" + "id=" + id + ", orden=" + orden + ", metodoPago=" + metodoPago + ", fecha_pago=" + fecha_pago + ", monto_total=" + monto_total + '}';
    } 
}
