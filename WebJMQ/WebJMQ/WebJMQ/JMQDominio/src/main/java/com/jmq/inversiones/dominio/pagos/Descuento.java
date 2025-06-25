package com.jmq.inversiones.dominio.pagos;

public class Descuento {
    private int id;
    private double numDescuento;
    private boolean activo;
    
    public double getNumDescuento() {
        return numDescuento;
    }

    public void setNumDescuento(double numDescuento) {
        this.numDescuento = numDescuento;
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Descuento() {
    }

    public Descuento(int id, int numDescuento) {
        this.id = id;
        this.numDescuento = numDescuento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Descuento{" + "id=" + id + ", porcentaje=" + numDescuento + '}';
    }
    
}
