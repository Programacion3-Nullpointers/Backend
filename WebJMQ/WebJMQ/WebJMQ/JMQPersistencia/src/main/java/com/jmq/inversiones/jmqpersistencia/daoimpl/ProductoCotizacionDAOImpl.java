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
        return "{CALL PRODUCTOCOTIZACION_INSERTAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL PRODUCTOCOTIZACION_MODIFICAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL PRODUCTOCOTIZACION_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{CALL PRODUCTOCOTIZACION_OBTENER(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL PRODUCTOCOTIZACION_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        CallableStatement cs = (CallableStatement)ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, entity.getDescripcion());
        cs.setInt(3, entity.getCantidad());
        cs.setDouble(4, entity.getPrecioCotizado());
        cs.setInt(5, entity.getFid_cotizacion());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        CallableStatement cs = (CallableStatement)ps;
        cs.setString(1, entity.getDescripcion());
        cs.setInt(2, entity.getCantidad());
        cs.setDouble(3, entity.getPrecioCotizado());
        cs.setInt(4, entity.getId());
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
