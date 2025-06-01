package com.jmq.inversiones.dominio.notificaciones;

import java.util.Date;

public class Notificacion {
    private int id;
    private String tipo;
    private String mensaje;
    private Date fecha_envio;
    private String estado;
    private String asunto;
    private String destinatario;
    
    public Notificacion(){
        
    }

    public Notificacion(int id, String tipo, String mensaje, Date fecha_envio, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fecha_envio = fecha_envio;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "id=" + id + ", tipo=" + tipo + ", mensaje=" + mensaje + ", fecha_envio=" + fecha_envio + ", estado=" + estado + '}';
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario the destinatario to set
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    
    
    
}
