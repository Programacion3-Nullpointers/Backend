package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.usuario.Usuario;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public interface UsuarioDAO extends BaseDAO<Usuario>{
    Usuario obtenerPorCorreo(String correo);
    
    void actualizarTokenRecuperacion(int idUsuario, String token, Date fechaExpiracion) throws Exception;
    Usuario obtenerPorToken(String token);
    List<Usuario> filtrarUsuarios(String tipoEntidad, Boolean activo) throws SQLException;
}
