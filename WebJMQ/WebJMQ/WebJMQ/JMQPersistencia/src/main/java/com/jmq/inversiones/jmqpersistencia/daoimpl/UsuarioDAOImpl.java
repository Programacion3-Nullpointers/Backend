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
        return "{CALL USUARIO_INSERTAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL USUARIO_MODIFICAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
        
        cs.setDouble(11, usuario.getSaldo());
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

        cs.setDouble(11, usuario.getSaldo());
    }

   @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usu = new Usuario();
        usu.setId(rs.getInt("idUsuario"));
        usu.setNombreUsuario(rs.getString("nombreUsuario"));
        usu.setContrasena(rs.getString("contrasena"));
        usu.setActivo(rs.getBoolean("activo"));
        usu.setCorreo(rs.getString("correo"));

        String tipoUsuarioStr = rs.getString("tipoUsuario");

        if (tipoUsuarioStr == null || tipoUsuarioStr.trim().isEmpty()) {
            throw new SQLException("El tipo de usuario es nulo o vacío para el id: " + usu.getId());
        }

        // Convertir el String a Enum.
        TipoUsuario tipo = TipoUsuario.valueOf(tipoUsuarioStr.toUpperCase());
        usu.setTipoUsuario(tipo);
        usu.setDireccion(rs.getString("direccion"));
        switch (tipo) {
            case EMPRESA:
                usu.setRUC(rs.getString("RUC"));
                usu.setRazonsocial(rs.getString("razonsocial"));
                usu.setDni(null);
                break;

            case CLIENTE:
                usu.setDni(rs.getString("dni"));
                usu.setRUC(null);
                usu.setRazonsocial(null);
                break;

             case ADMIN:
                 usu.setDni(null);
                 break;
        }
        usu.setSaldo(rs.getDouble("saldo"));
        return usu;
    }


    @Override
    protected void setId(Usuario entity, Integer id) {
        entity.setId(id);
    }

    // Los métodos agregar, actualizar, eliminar y listarTodos se heredan del BaseDAOImpl

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        String sql ="SELECT * FROM Usuario WHERE LOWER(TRIM(correo)) = LOWER(?)";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo.trim().toLowerCase());
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
    public List<Usuario> filtrarUsuarios(String tipoEntidad, Boolean activo) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM Usuario WHERE 1=1";
        List<Object> params = new ArrayList<>();

        // Filtro por tipo de entidad: empresa (RUC no nulo/vacío) o persona (RUC nulo o vacío)
        if (tipoEntidad != null) {
            if (tipoEntidad.equalsIgnoreCase("empresa")) {
                sql += " AND RUC IS NOT NULL AND RUC <> ''";
            } else if (tipoEntidad.equalsIgnoreCase("persona")) {
                sql += " AND (RUC IS NULL OR RUC = '')";
            }
            // No agregamos 'tipoEntidad' a params porque no se usa como parámetro en el SQL
        }

        // Filtro por estado activo/inactivo
        if (activo != null) {
            sql += " AND activo = ?";
            params.add(activo ? 1 : 0);
        }

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = createFromResultSet(rs);
                usuarios.add(u);
            }
        }

        return usuarios;
    }
    
    public void eliminarTest(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Usuario WHERE idUsuario = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }
}
