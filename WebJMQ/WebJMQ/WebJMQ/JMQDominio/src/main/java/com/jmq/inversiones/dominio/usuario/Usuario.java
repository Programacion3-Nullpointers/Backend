package com.jmq.inversiones.dominio.usuario;

import java.util.Date;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private boolean activo;
    private String correo;
    private TipoUsuario tipoUsuario;
    private String dni;
    private String razonsocial;
    private String direccion;
    private String RUC;
    private String token_reset;
    private Date fecha_expiracion_token;
    
    public Usuario(){
        
    }

    public Usuario(int id, String nombreUsuario, String contrasena, boolean activo, String correo, TipoUsuario tipoUsuario, String razonsocial, String direccion, String RUC) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.activo = activo;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.RUC = RUC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }
    
    public String getToken_reset() {
        return token_reset;
    }

    public void setToken_reset(String token_reset) {
        this.token_reset = token_reset;
    }

    public Date getFecha_expiracion_token() {
        return fecha_expiracion_token;
    }

    public void setFecha_expiracion_token(Date fecha_expiracion_token) {
        this.fecha_expiracion_token = fecha_expiracion_token;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", activo=" + activo +
                ", correo='" + correo + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", dni='" + dni + '\'' +
                ", razonsocial='" + razonsocial + '\'' +
                ", direccion='" + direccion + '\'' +
                ", RUC='" + RUC + '\'' +
                ", token_reset='" + token_reset + '\'' +
                ", fecha_expiracion_token=" + fecha_expiracion_token +
                '}';
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
}
