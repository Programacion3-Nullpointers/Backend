package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAOImpl extends BaseDAOImpl<Producto> implements ProductoDAO {

    private final CategoriaDAOImpl categoria;
    
    public ProductoDAOImpl(){
        categoria = new CategoriaDAOImpl();
    }
    
    @Override
    protected String getInsertQuery() {
        return "{CALL PRODUCTO_INSERTAR( ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL PRODUCTO_MODIFICAR( ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL PRODUCTO_ELIMINAR( ? )}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL PRODUCTO_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Producto entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Producto entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Producto createFromResultSet(ResultSet rs) throws SQLException {
        Producto prod = new Producto();
        prod.setId(rs.getInt("idProducto"));
        prod.setNombre(rs.getString("nombre"));
        prod.setDescripcion(rs.getString("descripcion"));
        prod.setPrecio(rs.getDouble("precio"));
        prod.setStock(rs.getInt("stock"));
        prod.setImagen(rs.getString("Imagen"));
        prod.setActivo(rs.getBoolean("activo"));
        prod.setCategoria(categoria.obtener(rs.getInt("idCategoria")));
        
        return prod;
    }

    @Override
    protected void setId(Producto entity, Integer id) {
        entity.setId(id);
    }
    
    @Override
    public void agregar(Producto pro) {
        // Sobrescribimos el método para usar el SP
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Registrar la venta
                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
                    
                    cs.registerOutParameter(1, Types.INTEGER);
                    cs.setString(2, pro.getNombre());
                    cs.setString(3, pro.getDescripcion());
                    cs.setInt(4, pro.getStock());
                    cs.setDouble(5, pro.getPrecio());
                    cs.setString(6, pro.getImagen());
                    cs.setBoolean(7, pro.isActivo());
                    cs.setInt(8,pro.getCategoria().getId());
                    cs.execute();
                    setId(pro, cs.getInt(1));
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
    public void actualizar(Producto pro) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, pro.getId());
            cs.setString(2, pro.getNombre());
            cs.setString(3, pro.getDescripcion());
            cs.setInt(4, pro.getStock());
            cs.setDouble(5, pro.getPrecio());
            cs.setString(6, pro.getImagen());
            cs.setBoolean(7, pro.isActivo());
            cs.setInt(8, pro.getCategoria().getId());
            
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
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getSelectAllQuery());
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                productos.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar descuentos", e);
        }
        return productos;
    }

    @Override
    public void descontarStock(int id, int stock) {
        String query = "UPDATE Producto SET stock = stock - ? WHERE idProducto = ?"; 
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al descontar stock", e);
        }
    }
    
    
}
