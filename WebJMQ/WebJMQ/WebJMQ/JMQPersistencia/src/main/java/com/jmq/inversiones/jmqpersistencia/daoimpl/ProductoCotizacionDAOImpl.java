package com.jmq.inversiones.jmqpersistencia.daoimpl;




import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO {

    
    
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
        return "DELETE FROM productoCotizado WHERE idCotizacion=?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM productoCotizado WHERE idproductoCotizado = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM productoCotizado";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.registerOutParameter(1, Types.INTEGER);
        ps.setString(2, entity.getDescripcion());
        ps.setInt(3, entity.getCantidad());
        ps.setDouble(4, entity.getPrecioCotizado());
        ps.setInt(5, entity.getFid_cotizacion());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setString(1, entity.getDescripcion());
        ps.setInt(2, entity.getCantidad());
        ps.setDouble(3, entity.getPrecioCotizado());
        ps.setInt(4, entity.getId());
    }

    @Override
    protected ProductoCotizacion createFromResultSet(ResultSet rs) throws SQLException {
        ProductoCotizacion prod = new ProductoCotizacion();
        prod.setId(rs.getInt("idproductoCotizado"));
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
    public void actualizarPrecioCotizacion(ProductoCotizacion pro,double precio) {
        
        pro.setPrecioCotizado(precio);
        actualizar(pro);
    }

 
}
