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
        return "{CALL USUARIO_MODIFICAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)}";
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
        cs.setBoolean(4, usuario.isActivo() == true);
        cs.setString(5, usuario.getCorreo());
        cs.setString(6, usuario.getTipoUsuario().name());
        cs.setString(7, usuario.getDni());
        cs.setString(8, usuario.getRazonsocial());
        cs.setString(9, usuario.getDireccion());
        cs.setString(10, usuario.getRUC());
        
        if (usuario.getDni() != null) {
            cs.setString(7, usuario.getDni());
        } else {
            cs.setNull(7, java.sql.Types.VARCHAR);
        }

        if (usuario.getRazonsocial() != null) {
            cs.setString(8, usuario.getRazonsocial());
        } else {
            cs.setNull(8, java.sql.Types.VARCHAR);
        }

        cs.setString(9, usuario.getDireccion());

        if (usuario.getRUC() != null) {
            cs.setString(10, usuario.getRUC());
        } else {
            cs.setNull(10, java.sql.Types.VARCHAR);
        }
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
        
        cs.setString(11, usuario.getToken_reset());
        if (usuario.getFecha_expiracion_token() != null) {
            cs.setTimestamp(12, new Timestamp(usuario.getFecha_expiracion_token().getTime()));
        } else {
            cs.setNull(12, Types.TIMESTAMP);
        }

    }

   @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usu = new Usuario();
        usu.setId(rs.getInt("idUsuario"));
        usu.setNombreUsuario(rs.getString("nombreUsuario"));
        usu.setContrasena(rs.getString("contrasena"));
        usu.setActivo(rs.getBoolean("activo"));
        usu.setCorreo(rs.getString("correo"));

        // 1. Obtenemos el tipo de usuario UNA SOLA VEZ para más eficiencia.
        String tipoUsuarioStr = rs.getString("tipoUsuario");

        // Si el valor de la base de datos es nulo o está vacío, no podemos continuar.
        if (tipoUsuarioStr == null || tipoUsuarioStr.trim().isEmpty()) {
            // Puedes lanzar una excepción o simplemente no devolver el usuario.
            throw new SQLException("El tipo de usuario es nulo o vacío para el id: " + usu.getId());
        }

        // 2. Convertimos el String a Enum.
        TipoUsuario tipo = TipoUsuario.valueOf(tipoUsuarioStr.toUpperCase());
        usu.setTipoUsuario(tipo);

        // 3. Usamos un 'switch' para manejar cada caso de forma explícita y segura.
        //    Es mucho más limpio y fácil de mantener que un if-else.
        switch (tipo) {
            case EMPRESA:
                usu.setRUC(rs.getString("RUC"));
                usu.setRazonsocial(rs.getString("razonsocial"));
                // Nos aseguramos de que los otros campos queden nulos.
                usu.setDni(null);
                break;

            case CLIENTE:
                usu.setDni(rs.getString("dni"));
                // Nos aseguramos de que los otros campos queden nulos.
                usu.setRUC(null);
                usu.setRazonsocial(null);
                break;

             case ADMIN:
                 usu.setDni(null);
            //     usu.setRUC(null);
            //     usu.setRazonsocial(null);
                 break;

        }
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

    @Override
    public Usuario obtenerPorToken(String token) {
        String sql = "SELECT * FROM Usuario WHERE token_reset = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = createFromResultSet(rs);

                    // Si manejas expiración en BD, agrégalo aquí
                    Timestamp fechaExp = rs.getTimestamp("fecha_expiracion_token");
                    if (fechaExp != null) {
                        usuario.setFecha_expiracion_token(new java.util.Date(fechaExp.getTime()));
                    }

                    usuario.setToken_reset(token);
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por token", e);
        }
        return null;
    }


}
