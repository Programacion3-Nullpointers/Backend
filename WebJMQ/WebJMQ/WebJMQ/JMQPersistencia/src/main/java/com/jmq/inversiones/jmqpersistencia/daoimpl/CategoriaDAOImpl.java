package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.jmqpersistencia.dao.CategoriaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class CategoriaDAOImpl extends BaseDAOImpl<Categoria> implements CategoriaDAO{

    private final DescuentoDAOImpl descuentoDAO;
    
    
    public CategoriaDAOImpl(){
        this.descuentoDAO = new DescuentoDAOImpl();
    }
    
    
    @Override
    protected String getInsertQuery() {
        return "{CALL CATEGORIA_INSERTAR( ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL CATEGORIA_MODIFICAR(?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL CATEGORIA_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL CATEGORIA_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Categoria entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Categoria entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Categoria createFromResultSet(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria();
        
        cat.setId(rs.getInt("idCategoria"));
        cat.setNombre(rs.getString("nombre"));
        cat.setDescripcion(rs.getString("descripcion"));
        cat.setDescuento(descuentoDAO.obtener(rs.getInt("idDescuento")));
        
        return cat;
    }

    @Override
    protected void setId(Categoria entity, Integer id) {
        entity.setId(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    @Override
    public void agregar(Categoria cat) {
        // Sobrescribimos el método para usar el SP
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Registrar el descuento
                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
                    
                    cs.registerOutParameter(1, Types.INTEGER);
                    cs.setString(2, cat.getDescripcion());
                    cs.setString(2, cat.getNombre());
                    cs.setInt(4, cat.getDescuento().getId());
                    cs.execute();
                    setId(cat, cs.getInt(1));
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
    public void actualizar(Categoria cat) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, cat.getId());
            cs.setString(2, cat.getDescripcion());
            cs.setString(3, cat.getNombre());
            cs.setInt(4, cat.getDescuento().getId());
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
            //cs.setDouble(2, desc.getPorcentaje());
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    
    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getSelectAllQuery());
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                categorias.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar descuentos", e);
        }
        return categorias;
    }
    
}
