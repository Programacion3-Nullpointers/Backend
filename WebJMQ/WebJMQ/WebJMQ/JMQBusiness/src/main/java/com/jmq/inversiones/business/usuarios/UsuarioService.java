package com.jmq.inversiones.business.usuarios;

import com.jmq.inversiones.dominio.usuario.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario) throws Exception;
    void actualizarUsuario(Usuario usuario) throws Exception;
    void eliminarUsuario(int id) throws Exception;
    Usuario buscarUsuario(int id) throws Exception;
    List<Usuario> listarUsuarios() throws Exception;
    Usuario buscarUsuarioPorCorreo(String correo) throws Exception;
    void iniciarRecuperacionPassword(String correo) throws Exception;
    boolean cambiarPasswordConToken(String token, String nuevaPassword) throws Exception;
    boolean validarTokenPassword(String token);
    List<Usuario> filtrarUsuarios(String tipoEntidad, Boolean activo) throws SQLException;
    Usuario verificarCredenciales(String correo, String password) throws Exception;
}
