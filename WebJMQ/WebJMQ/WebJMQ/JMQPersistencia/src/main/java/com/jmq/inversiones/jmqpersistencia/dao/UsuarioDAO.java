package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.usuario.Usuario;
import java.util.Date;


public interface UsuarioDAO extends BaseDAO<Usuario>{
    Usuario obtenerPorCorreo(String correo);
    void actualizarTokenRecuperacion(int idUsuario, String nueva) throws Exception;

    Usuario obtenerPorToken(String token);
}
