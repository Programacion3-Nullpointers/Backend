package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO{

    
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO Cotizacion (idproductoCotizado,descripcion,cantidad,"
                + "precioCotizado,idCotizacion) VALUES ( ?, ?, ?, ?, ? )";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE productoCotizado SET precioCotizado = ?"
                + " WHERE idproductoCotizado = ? AND idCotizacion = ?";
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
        //return "SELECT * FROM productoCotizado WHERE idCotizacion = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setInt(1, entity.getId());
        ps.setString(2, entity.getDescripcion());
        ps.setInt(3, entity.getCantidad());
        ps.setDouble(4, entity.getPrecioCotizado());
        ps.setInt(5, entity.getFid_cotizacion());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setDouble(1, entity.getPrecioCotizado());
        ps.setInt(2, entity.getId());
        ps.setInt(3, entity.getFid_cotizacion());
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

    
    @Override
    public void actualizarPrecioCotizacion(Integer id, Integer fid,double precio) {
        ProductoCotizacion pro = new ProductoCotizacion();
        pro.setId(id);
        pro.setFid_cotizacion(fid);
        pro.setPrecioCotizado(precio);
        actualizar(pro);
    }

    
    
    
}
