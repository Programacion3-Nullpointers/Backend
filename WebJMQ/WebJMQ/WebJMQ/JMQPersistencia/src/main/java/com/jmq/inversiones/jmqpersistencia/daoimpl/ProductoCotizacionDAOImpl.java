package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO{

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
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected ProductoCotizacion createFromResultSet(ResultSet rs) throws SQLException {
        ProductoCotizacion prod = new ProductoCotizacion();
        
        prod.setId(rs.getInt("idproductoCotizacion"));
        prod.setCantidad(rs.getInt("cantidad"));
        prod.setDescripcion(rs.getString("descripcion"));
        prod.setPrecioCotizado(rs.getDouble("precioCotizado"));
        
        return prod;
    }

    @Override
    protected void setId(ProductoCotizacion entity, Integer id) {
        entity.setId(id);
    }

    void agregar(ProductoCotizacion pc, int id, Connection conn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void eliminarPorCotizacion(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void agregar(ProductoCotizacion pc, int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<ProductoCotizacion> obtenerPorCotizacion(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
