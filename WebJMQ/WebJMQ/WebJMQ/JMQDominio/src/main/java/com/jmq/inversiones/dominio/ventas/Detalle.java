package com.jmq.inversiones.dominio.ventas;

public class Detalle {
    private int id;
    private Producto producto;
    private int cantidad;
    private double precio_unitario;

    public Detalle() {
    }

    public Detalle(int id, Producto producto, int cantidad, double precio_unitario) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    @Override
    public String toString() {
        return "Detalle{" + "id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + ", precio_unitario=" + precio_unitario + '}';
    }
    
    
}
