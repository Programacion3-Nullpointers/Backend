package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;

import java.sql.*;

public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO {

    @Override
    protected String getInsertQuery() {
        return "{CALL USUARIO_INSERTAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL USUARIO_MODIFICAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL USUARIO_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{call sp_buscar_usuario_por_id(?)}";
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
        cs.setString(7, usuario.getDni());
        cs.setString(8, usuario.getRazonsocial());
        cs.setString(9, usuario.getDireccion());
        cs.setString(10, usuario.getRUC());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Usuario usuario) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, usuario.getId());

        // nombreUsuario puede ser null
        if (usuario.getNombreUsuario() != null)
            cs.setString(2, usuario.getNombreUsuario());
        else
            cs.setNull(2, Types.VARCHAR);

        // contrasena NO puede ser null
        if (usuario.getContrasena() == null)
            throw new SQLException("La contraseña no puede ser null");
        cs.setString(3, usuario.getContrasena());

        cs.setBoolean(4, usuario.isActivo());

        // correo NO puede ser null
        if (usuario.getCorreo() == null)
            throw new SQLException("El correo no puede ser null");
        cs.setString(5, usuario.getCorreo());

        // tipoUsuario NO puede ser null
        if (usuario.getTipoUsuario() == null)
            throw new SQLException("El tipo de usuario no puede ser null");
        cs.setString(6, usuario.getTipoUsuario().name());

        // dni puede ser null
        if (usuario.getDni() != null)
            cs.setString(7, usuario.getDni());
        else
            cs.setNull(7, Types.VARCHAR);

        // razonsocial puede ser null
        if (usuario.getRazonsocial() != null)
            cs.setString(8, usuario.getRazonsocial());
        else
            cs.setNull(8, Types.VARCHAR);

        // direccion puede ser null
        if (usuario.getDireccion() != null)
            cs.setString(9, usuario.getDireccion());
        else
            cs.setNull(9, Types.VARCHAR);

        // RUC puede ser null
        if (usuario.getRUC() != null)
            cs.setString(10, usuario.getRUC());
        else
            cs.setNull(10, Types.VARCHAR);
        
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
        usu.setDni(rs.getString("dni"));
        usu.setRazonsocial(rs.getString("razonsocial"));
        usu.setDireccion(rs.getString("direccion"));
        usu.setRUC(rs.getString("RUC"));
        
        return usu;
    }

    @Override
    protected void setId(Usuario entity, Integer id) {
        entity.setId(id);
    }

    // Los métodos agregar, actualizar, eliminar y listarTodos se heredan del BaseDAOImpl

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        String sql ="SELECT * FROM Usuario WHERE correo = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entidad", e);
        }
        return null;
    }

}
