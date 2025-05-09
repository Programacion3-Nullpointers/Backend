package com.jmq.inversiones.dominio.contizaciones;

public class ProductoCotizacion {
    private int id;
    private String descripcion;
    private int cantidad;
    private double precioCotizado;
    
    public ProductoCotizacion(){
        
    }

    public ProductoCotizacion(int id, String descripcion, int cantidad, double precioCotizado, Cotizacion cotizacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioCotizado = precioCotizado;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCotizado() {
        return precioCotizado;
    }

    public void setPrecioCotizado(double precioCotizado) {
        this.precioCotizado = precioCotizado;
    }
    
}
