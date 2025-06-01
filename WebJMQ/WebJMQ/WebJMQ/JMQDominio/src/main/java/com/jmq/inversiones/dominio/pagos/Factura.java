package com.jmq.inversiones.dominio.pagos;

import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.Date;

public class Factura extends ComprobantePago {
    private String RUC;
    private String razon_social;
    private String direccion;
    private Date fecha_emision;

    public Factura() {
        
    }

    public Factura(String RUC, String razon_social, String direccion, Date fecha_emision, int id, OrdenVenta orden, MetodoPago metodoPago, Date fecha_pago, double monto_total) {
        super(id, orden, metodoPago, fecha_pago, monto_total);
        this.RUC = RUC;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.fecha_emision = fecha_emision;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    @Override
    public String toString() {
        return super.toString() + "Factura{" + "RUC=" + RUC + ", razon_social=" + razon_social + ", direccion=" + direccion + ", fecha_emision=" + fecha_emision + '}';
    }
    
}
