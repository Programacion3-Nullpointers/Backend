package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DetalleDAOImpl extends BaseDAOImpl<Detalle> implements DetalleDAO{

    private final ProductoDAOImpl producto;
    
    public DetalleDAOImpl(){
        this.producto = new ProductoDAOImpl();
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
    protected void setInsertParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Detalle createFromResultSet(ResultSet rs) throws SQLException {
        Detalle detalle = new Detalle();
        detalle.setId(rs.getInt("idDetalle"));
        detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setProducto(producto.obtener(rs.getInt("idProducto")));
        
        return detalle;
    }

    @Override
    protected void setId(Detalle entity, Integer id) {
        entity.setId(id);
    }
    
}
