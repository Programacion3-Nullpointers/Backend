package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO{

    @Override
    protected String getInsertQuery() {
        return "{CALL USUARIO_INSERTAR(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL USUARIO_MODIFICAR( ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL USUARIO_ELIMINAR( ? )}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL USUARIO_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Usuario entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Usuario entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usu = new Usuario();
        
        usu.setId(rs.getInt("idUsuario"));
        usu.setDireccion(rs.getString("direccion"));
        usu.setCorreo(rs.getString("correo"));
        usu.setActivo(rs.getBoolean("activo"));
        usu.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipoUsuario")));
        if(rs.getString("tipoUsuario").equals("EMPRESA")){
            usu.setRUC(rs.getString("RUC"));
            usu.setRazonsocial(rs.getString("razonsocial"));
        }
        else{
            usu.setNombreUsuario(rs.getString("nombreUsuario"));
            usu.setDni(rs.getString("dni"));
        }
        
        
        return usu;
    }

    @Override
    protected void setId(Usuario entity, Integer id) {
        entity.setId(id);
    }
    
    @Override
    public void agregar(Usuario usu) {
        // Sobrescribimos el método para usar el SP
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Registrar el descuento
                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
                    
                    cs.registerOutParameter(1, Types.INTEGER);
                    cs.setString(2, usu.getNombreUsuario());
                    cs.setString(3, usu.getContrasena());
                    cs.setBoolean(4, usu.isActivo());
                    cs.setString(5, usu.getCorreo());
                    cs.setString(6, usu.getTipoUsuario().name());
                    cs.setString(7, usu.getRazonsocial());
                    cs.setString(8, usu.getDireccion());
                    cs.setString(9, usu.getRUC());
                    cs.execute();
                    setId(usu, cs.getInt(1));
                }
                
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar descuento", e);
        }
    }
    
    @Override
    public void actualizar(Usuario usu) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, usu.getId());
            cs.setString(2, usu.getNombreUsuario());
            cs.setString(3, usu.getContrasena());
            cs.setBoolean(4, usu.isActivo());
            cs.setString(5, usu.getCorreo());
            cs.setString(6, usu.getTipoUsuario().name());
            cs.setString(7, usu.getRazonsocial());
            cs.setString(8, usu.getDireccion());
            cs.setString(9, usu.getRUC());
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    
    @Override
    public void eliminar(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1,id);
            
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getSelectAllQuery());
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar descuentos", e);
        }
        return usuarios;
    }
    
    
}
