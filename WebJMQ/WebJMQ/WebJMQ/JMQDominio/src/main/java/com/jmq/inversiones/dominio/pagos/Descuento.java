package com.jmq.inversiones.dominio.pagos;

public class Descuento {
    private int id;
    private int num_porcentaje;

    public Descuento() {
    }

    public Descuento(int id, int porcentaje) {
        this.id = id;
        this.num_porcentaje = porcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPorcentaje() {
        return num_porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.num_porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return "Descuento{" + "id=" + id + ", porcentaje=" + num_porcentaje + '}';
    }
    
}
