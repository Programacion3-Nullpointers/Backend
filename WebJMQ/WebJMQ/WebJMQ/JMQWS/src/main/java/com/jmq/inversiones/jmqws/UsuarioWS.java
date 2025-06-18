package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.business.impl.UsuarioServiceImpl;
import com.jmq.inversiones.dominio.usuario.Usuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {

    private final UsuarioService usuarioService;
    
    public UsuarioWS(){
        this.usuarioService = new UsuarioServiceImpl();
    }

    @WebMethod(operationName = "registrarUsuario")
    public void registrarUsuario(@WebParam(name = "usuario") Usuario usuario) throws Exception {
        usuarioService.registrarUsuario(usuario);
    }

    @WebMethod(operationName = "actualizarUsuario")
    public void actualizarUsuario(@WebParam(name = "usuario") Usuario usuario) throws Exception {
        usuarioService.actualiarUsuario(usuario);
    }

    @WebMethod(operationName = "eliminarUsuario")
    public void eliminarUsuario(@WebParam(name = "id") int id) throws Exception {
        usuarioService.eliminarUsuario(id);
    }

    @WebMethod(operationName = "buscarUsuario")
    public Usuario buscarUsuario(@WebParam(name = "id") int id) throws Exception {
        return usuarioService.buscarUsuario(id);
    }

    @WebMethod(operationName = "listarUsuarios")
    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioService.listarUsuarios();
    }
    
    @WebMethod(operationName = "BuscarUsuarioPorCorreo")
    public Usuario buscarUsuarioPorCorreo(@WebParam(name = "id") String correo) throws Exception {
        return usuarioService.buscarUsuarioPorCorreo(correo);
    }
    
     @WebMethod
    public void iniciarRecuperacionPassword(String email) throws Exception {
        usuarioService.iniciarRecuperacionPassword(email);
    }

    @WebMethod
    public boolean cambiarPasswordConToken(String token, String nuevaPassword) throws Exception {
        return usuarioService.cambiarPasswordConToken(token, nuevaPassword);
    }

    @WebMethod
    public Usuario obtenerPorToken(String token) throws Exception {
        return usuarioService.obtenerPorToken(token);
    }
}
