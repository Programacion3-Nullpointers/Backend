package com.jmq.inversiones.dominio.ventas;

import com.jmq.inversiones.dominio.pagos.Descuento;

public class Categoria {
    private int id;
    private String descripcion;
    private String nombre;
    private Descuento descuento;

    public Categoria() {
    }

    public Categoria(int id, String descripcion, String nombre, Descuento descuento) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", descripcion=" + descripcion + ", nombre=" + nombre + ", descuento=" + descuento + '}';
    }
    
    
}
