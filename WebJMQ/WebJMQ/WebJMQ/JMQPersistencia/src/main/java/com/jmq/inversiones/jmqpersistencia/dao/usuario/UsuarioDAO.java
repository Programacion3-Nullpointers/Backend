package com.jmq.inversiones.jmqpersistencia.dao.usuario;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.usuario.Usuario;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public interface UsuarioDAO extends BaseDAO<Usuario>{
    Usuario obtenerPorCorreo(String correo);
    List<Usuario> filtrarUsuarios(String tipoEntidad, Boolean activo) throws SQLException;
}
