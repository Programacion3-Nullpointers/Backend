package com.jmq.inversiones.dominio.ventas;

public class Detalle {
    private OrdenVenta orden;
    private Producto producto;
    private int cantidad;
    private double precio_unitario;

    public Detalle() {
    }

    public Detalle(OrdenVenta orden,Producto producto, int cantidad, double precio_unitario) {
        this.orden=orden;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
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
        return "Detalle{" + "ordenVenta=" + orden+ ", producto=" + producto + ", cantidad=" + cantidad + ", precio_unitario=" + precio_unitario + '}';
    }

    /**
     * @return the orden
     */
    public OrdenVenta getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(OrdenVenta orden) {
        this.orden = orden;
    }
    
    
}
