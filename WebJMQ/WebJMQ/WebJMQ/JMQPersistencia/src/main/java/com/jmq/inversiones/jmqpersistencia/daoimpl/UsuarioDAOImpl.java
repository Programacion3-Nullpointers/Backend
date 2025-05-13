package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO {

    @Override
    protected String getInsertQuery() {
        return "{CALL USUARIO_INSERTAR(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL USUARIO_MODIFICAR(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL USUARIO_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("No implementado: deberías crear USUARIO_OBTENER");
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL USUARIO_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Usuario usuario) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER); // OUT id
        cs.setString(2, usuario.getNombreUsuario());
        cs.setString(3, usuario.getContrasena());
        cs.setBoolean(4, usuario.isActivo());
        cs.setString(5, usuario.getCorreo());
        cs.setString(6, usuario.getTipoUsuario().name());
        cs.setString(7, usuario.getRazonsocial());
        cs.setString(8, usuario.getDireccion());
        cs.setString(9, usuario.getRUC());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Usuario usuario) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, usuario.getId());
        cs.setString(2, usuario.getNombreUsuario());
        cs.setString(3, usuario.getContrasena());
        cs.setBoolean(4, usuario.isActivo());
        cs.setString(5, usuario.getCorreo());
        cs.setString(6, usuario.getTipoUsuario().name());
        cs.setString(7, usuario.getRazonsocial());
        cs.setString(8, usuario.getDireccion());
        cs.setString(9, usuario.getRUC());
    }

    @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usu = new Usuario();
        usu.setId(rs.getInt("idUsuario"));
        usu.setNombreUsuario(rs.getString("nombreUsuario"));
        usu.setContrasena(rs.getString("contrasena"));
        usu.setActivo(rs.getBoolean("activo"));
        usu.setCorreo(rs.getString("correo"));
        usu.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipoUsuario")));
        usu.setRazonsocial(rs.getString("razonsocial"));
        usu.setDireccion(rs.getString("direccion"));
        usu.setRUC(rs.getString("RUC"));

        // El campo `dni` está en la clase pero no en el SP ni el ResultSet
        // Si decides usarlo, deberías modificar el SP y agregarlo al SELECT
        return usu;
    }

    @Override
    protected void setId(Usuario entity, Integer id) {
        entity.setId(id);
    }

    // Los métodos agregar, actualizar, eliminar y listarTodos se heredan del BaseDAOImpl
}
