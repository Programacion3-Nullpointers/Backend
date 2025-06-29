package com.jmq.inversiones.dominio.usuario;

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
    private double saldo;
    
    public Usuario(){
        
    }

    public Usuario(int id, String nombreUsuario, String contrasena, boolean activo, String correo, TipoUsuario tipoUsuario, String razonsocial, 
            String direccion, String RUC, double saldo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.activo = activo;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.RUC = RUC;
        this.saldo = saldo;
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
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
 