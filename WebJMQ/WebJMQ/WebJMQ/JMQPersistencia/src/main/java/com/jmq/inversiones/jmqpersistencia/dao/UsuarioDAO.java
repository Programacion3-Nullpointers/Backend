package com.jmq.inversiones.jmqpersistencia.dao;

import com.jmq.inversiones.jmqpersistencia.BaseDAO;
import com.jmq.inversiones.dominio.usuario.Usuario;


public interface UsuarioDAO extends BaseDAO<Usuario>{
    Usuario obtenerPorCorreo(String correo);

    Usuario obtenerPorToken(String token);
}
