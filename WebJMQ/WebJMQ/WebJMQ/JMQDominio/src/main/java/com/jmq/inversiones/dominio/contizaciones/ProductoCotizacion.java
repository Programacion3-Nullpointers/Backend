package com.jmq.inversiones.dominio.contizaciones;

public class ProductoCotizacion {
    private int id;
    private String descripcion;
    private int cantidad;
    private double precioCotizado;
    private int fid_cotizacion;
    
    public ProductoCotizacion(){
        
    }

    public ProductoCotizacion(String descripcion, int cantidad, double precioCotizado, int fid_cotizacion) {
       this.id = id;
       this.descripcion = descripcion;
       this.cantidad = cantidad;
       this.precioCotizado = precioCotizado;
       this.fid_cotizacion = fid_cotizacion;
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

    /**
     * @return the fid_cotizacion
     */
    public int getFid_cotizacion() {
        return fid_cotizacion;
    }

    /**
     * @param fid_cotizacion the fid_cotizacion to set
     */
    public void setFid_cotizacion(int fid_cotizacion) {
        this.fid_cotizacion = fid_cotizacion;
    }
    
}
