package com.jmq.inversiones.dominio.pagos;

import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.util.Date;

public class Boleta extends ComprobantePago {
    private String dni;
    private String nombre;
    private Date fecha_emision;

    public Boleta() {
    }

    public Boleta(int id, OrdenVenta orden, MetodoPago metodoPago, Date fecha_pago, double monto_total,
              String dni, String nombre, Date fecha_emision) {
        super(id, orden, metodoPago, fecha_pago, monto_total);
        this.dni = dni;
        this.nombre = nombre;
        this.fecha_emision = fecha_emision;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    @Override
    public String toString() {
        return super.toString() + "Boleta{" + "dni=" + dni + ", nombre=" + nombre + ", fecha_emision=" + fecha_emision + '}';
    }
    
    
    
}
