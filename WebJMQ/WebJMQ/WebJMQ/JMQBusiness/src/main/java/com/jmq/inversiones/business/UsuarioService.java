package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.usuario.Usuario;
import java.util.List;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario) throws Exception;
    void actualiarUsuario(Usuario usuario) throws Exception;
    void eliminarUsuario(int id) throws Exception;
    Usuario buscarUsuario(int id) throws Exception;
    List<Usuario> listarUsuarios() throws Exception;
    
}
