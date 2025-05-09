package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductoDAOImpl extends BaseDAOImpl<Producto> implements ProductoDAO {

    private final CategoriaDAOImpl categoria;
    
    public ProductoDAOImpl(){
        categoria = new CategoriaDAOImpl();
    }
    
    @Override
    protected String getInsertQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getDeleteQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    
    
}
